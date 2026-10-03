package com.sistemaabil.system.model;

public abstract class Model {
    
    public abstract int getId();
    
    public boolean esNueva() {
        return getId() == 0;
    }
}
