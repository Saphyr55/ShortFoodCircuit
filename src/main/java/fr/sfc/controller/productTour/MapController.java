package fr.sfc.controller.productTour;

import fr.sfc.container.productTour.MapContainer;
import fr.sfc.entity.Customer;
import fr.sfc.entity.Order;
import fr.sfc.framework.Resources;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.injection.Inject;
import fr.sfc.framework.item.Tag;
import fr.sfc.repository.CustomerRepository;
import fr.sfc.repository.OrderRepository;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapController implements Controller {
    
    @AutoContainer
    private MapContainer component;

    private WebEngine engine;
    private JSObject js;

    @Inject
    @Tag("controller:root.list")
    private ListProductTourController listProductTourController;

    @Inject
    @Tag("controller:root")
    private MainProductTourController mainProductTourController;

    @Inject
    private OrderRepository orderRepository;
    @Inject
    private CustomerRepository customerRepository;

    private Set<Customer> customers;

    @Override
    public void setup() {
        engine = component.getWwMap().getEngine();
        engine.load(Resources.getResource("/html/map.html").toExternalForm());
        engine.getLoadWorker()
                .stateProperty()
                .addListener(this::loadJavascript);
    }

    public void loadMap() {
        customers = new HashSet<>();
        Set<Order> orders = orderRepository.findByProductTour(listProductTourController.getCurrentProductTour());
        orders.forEach(order -> customers.add(customerRepository.find(order.getId())));
        engine.reload();
    }

    private void loadJavascript(ObservableValue<? extends Worker.State> o,
                                Worker.State oldState,
                                Worker.State state) {

        if (state == Worker.State.SUCCEEDED) {
            try {

                js = (JSObject) engine.executeScript("window");

                final Object map = js.call("createMap",
                        mainProductTourController.getCurrentCompany().getLatitude(),
                        mainProductTourController.getCurrentCompany().getLongitude());

                final List<Object> markers = new ArrayList<>();
                if (customers != null)
                    customers.forEach(customer -> {
                        System.out.println(customers);
                        markers.add(createMarker(customer, map));
                    });

            } catch (JSException e) {
                e.printStackTrace();
            }

        }
    }

    private Object createMarker(Customer customer, Object map) {
        return js.call("createMaker", map,
                customer.getLatitude(), customer.getLongitude(),
                "<b>"+customer.getName()+"</b><br />"+customer.getAddress());
    }

}