package Examen;

import org.testng.annotations.Factory;


public class TestFactoryExamen {

    @Factory
    public Object[] prueba_netflix(){
        return new Object[]{
                new prueba_netflix("ledier@gmail.com"),
                new prueba_netflix("carlos@gmail.com")
        };
    }
}
