package fr.sfc.controller.productTour;

import fr.sfc.container.productTour.MapContainer;
import fr.sfc.controller.ConnectionController;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MapController implements Controller {
    
    @AutoContainer
    private MapContainer component;

    private WebEngine engine;
    private JSObject js;

    @Inject
    @Tag("controller:root.producer.list")
    private ListProductTourController listProductTourController;
    @Inject
    @Tag("controller:root")
    private ConnectionController connectionController;

    @Inject
    private OrderRepository orderRepository;
    @Inject
    private CustomerRepository customerRepository;

    private Set<Order> orders;

    private final String CREATE_MARKER_FUNCTION_NAME = "createMarker";
    private final String CREATE_MAP_FUNCTION_NAME = "createMap";

    @Override
    public void setup() {
        engine = component.getWwMap().getEngine();
        engine.load(Resources.getResource("/html/map.html").toExternalForm());
        engine.getLoadWorker()
                .stateProperty()
                .addListener(this::loadJavascript);
    }

    public void loadMap() {
        orders = orderRepository.findByProductTour(listProductTourController.getCurrentProductTour());
        engine.reload();
    }

    private void loadJavascript(ObservableValue<? extends Worker.State> o,
                                Worker.State oldState,
                                Worker.State state) {

        if (state == Worker.State.SUCCEEDED) {
            try {

                js = (JSObject) engine.executeScript("window");
                if (connectionController != null) {

                    final Object map = js.call(CREATE_MAP_FUNCTION_NAME,
                            connectionController.getCompanyConnected().getLatitude(),
                            connectionController.getCompanyConnected().getLongitude());
                    final List<Object> markers = new ArrayList<>();
                    if (orders != null)
                        orders.forEach(order -> markers.add(
                                createMarker(order, customerRepository.find(order.getIdCustomer()), map)));
                }

            } catch (JSException e) {
                e.printStackTrace();
            }

        }
    }

    private Object createMarker(Order order, Customer customer, Object map) {
        return js.call(CREATE_MARKER_FUNCTION_NAME, map,
                        customer.getLatitude(), customer.getLongitude(),
                        "<b>" + customer.getName() + "</b><br/>" +
                         customer.getAddress() + "<br/> Debut le "+
                        formatDate(order.getStartLocalDateTime()) + "<br/> Fin le " +
                        formatDate(order.getEndLocalDateTime()) + "<br/>");
    }

    private String formatDate(LocalDateTime localDateTime) {
        return localDateTime == null ? "unknown" : localDateTime.toString().replace('T', ' ');
    }

}