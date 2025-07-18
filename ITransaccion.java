public interface ITransaccion {
    boolean retirar(double monto);
    boolean pagar(double monto);
}