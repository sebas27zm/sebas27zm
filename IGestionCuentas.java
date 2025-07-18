import java.util.List;

public interface IGestionCuentas {
    void agregarCuenta(Cuenta cuenta);
    void removerCuenta(Cuenta cuenta);
    List<Cuenta> getCuentas();
    String reporteCuentas();
}