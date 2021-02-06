package Examen;

import org.testng.annotations.Factory;


public class TestFactoryExamen {

    @Factory
    public Object[] prueba_netflix(){
        return new Object[]{
                new TestSolofactory("ledier@gmail.com"),
                new TestSolofactory("carlos@gmail.com")
        };
    }
}
