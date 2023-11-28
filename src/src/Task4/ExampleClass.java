package Task4;

public class ExampleClass {
    // comment
    private int privateField;
    public String publicField;
    protected double protectedField;

    public ExampleClass() {
        // Конструктор за замовчуванням
    }

    public ExampleClass(int privateField, String publicField, double protectedField) {
        this.privateField = privateField;
        this.publicField = publicField;
        this.protectedField = protectedField;
    }

    public int getPrivateField() {// comment
        return privateField;
    }

    public void setPrivateField(int privateField) {
        this.privateField = privateField;
    }

    public void publicMethod() {
        System.out.println("Це публічний метод");
    }

    protected void protectedMethod() {
        System.out.println("Це захищений метод");
    }
    // comment
    private void privateMethod(int parameter) {
        System.out.println("Це приватний метод з параметром: " + parameter);
    }
}
