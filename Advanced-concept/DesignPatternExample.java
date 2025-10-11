package com.designpatterns;

interface Shape {
    void draw();
}

class Circle implements Shape {
    public void draw() {
        System.out.println("Drawing a Circle");
    }
}

class Square implements Shape {
    public void draw() {
        System.out.println("Drawing a Square");
    }
}

class ShapeFactory {
    public Shape getShape(String type) {
        if (type == null) return null;
        switch (type.toLowerCase()) {
            case "circle": return new Circle();
            case "square": return new Square();
            default: return null;
        }
    }
}

class SingletonService {
    private static volatile SingletonService instance;

    private SingletonService() {}

    public static SingletonService getInstance() {
        if (instance == null) {
            synchronized (SingletonService.class) {
                if (instance == null) {
                    instance = new SingletonService();
                }
            }
        }
        return instance;
    }

    public void serve() {
        System.out.println("SingletonService is serving...");
    }
}

public class DesignPatternExample {
    public static void main(String[] args) {
        SingletonService service = SingletonService.getInstance();
        service.serve();

        ShapeFactory factory = new ShapeFactory();
        Shape shape1 = factory.getShape("circle");
        Shape shape2 = factory.getShape("square");

        shape1.draw();
        shape2.draw();
    }
}
