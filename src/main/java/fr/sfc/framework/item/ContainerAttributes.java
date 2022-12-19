package fr.sfc.framework.item;

public record ContainerAttributes(String tag, String type, String controller) {
    
    @Override
    public String toString() {
        return "ContainerAttributes{" +
                "tag='" + tag + '\'' +
                ", type='" + type + '\'' +
                ", controller='" + controller + '\'' +
                '}';
    }

    public static class Builder {

        private String tag;
        private String type;
        private String controller;

        public Builder withTag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder withController(String controller) {
            this.controller = controller;
            return this;
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public ContainerAttributes build() {
            return new ContainerAttributes(tag, type, controller);
        }

    }


}
