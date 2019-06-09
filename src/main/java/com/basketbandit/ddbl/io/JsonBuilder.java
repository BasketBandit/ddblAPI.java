package com.basketbandit.ddbl.io;

import com.google.gson.JsonObject;

public class JsonBuilder {

    private JsonObject object;

    public JsonBuilder() {
        this.object = new JsonObject();
    }

    public JsonBuilder addProperty(String property, Number value) {
        this.object.addProperty(property, value);
        return this;
    }

    public JsonBuilder addProperty(String property, String value) {
        this.object.addProperty(property, value);
        return this;
    }

    public JsonBuilder addProperty(String property, Boolean value) {
        this.object.addProperty(property, value);
        return this;
    }

    public JsonBuilder addProperty(String property, Character value) {
        this.object.addProperty(property, value);
        return this;
    }

    public JsonObject getObject() {
        return object;
    }
}
