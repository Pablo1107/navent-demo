/*
 * Extract from PDF: 
 * 1- Se desean modelar los Pedidos realizados por un cliente. Las operaciones
 * que se necesitan son: crear pedidos, modificar pedidos, buscar pedidos por
 * id, y borrar pedidos.  Se pide modelar un servicio que implemente estas
 * operaciones (en lenguaje Java) sabiendo que se utiliza una estructura de
 * caches para no acceder a la base de datos en cada operación solicitada a
 * este servicio backend. El servicio es utilizado en un sitio web, con
 * usuarios concurrentes. Tomar como ya implementadas las clases BumexMemcached
 * y PedidosDAO, descritas más adelante.
 *
 * Datos:
 * Tabla Pedido:
 * Campos: idPedido, nombre, monto, descuento
 *
 * La clase BumexMemcached es un singleton que tiene los siguientes métodos
 * (tomarlos como ya implementados, no es necesario codificarlos):
 *
 *       void set(String key, Object value)  Object get(String key)  void
 *      delete(String key)
 *
 * La clase PedidosDAO tiene los siguientes métodos estáticos que actualizan la
 * tabla Pedido (tomarlos como ya implementados, no es necesario codificarlos):
 *
 *       void insertOrUpdate(Pedido pedido):
 *          inserta un nuevo pedido en la base de datos o modifica un pedido
 *          existente (en cado de crear uno nuevo, el pedido pasado como
 *          parámetro se completa con el nuevo id).
 *       void delete(Pedido pedido):
 *          elimina el pedido que corresponde al id recibido.
 *       Pedido select(Integer idPedido):
 *          busca un pedido por id.
 *
 * @author Pablo Andres Dealbera
 */
public class PedidoService {
    /* Assumptions:
     *  - The cache it's only used by this service
     *    (to simplify setting and getting using only the id)
     * 
     *  - The validations when creating Pedido object has been exectuted
     *    before calling the storeOrUpdate method here
     */

    private BumexMemcached cache = BumexMemcached.getCache();
    private PedidosDAO dao = new PedidosDAO();

    public void storeOrUpdate(Pedido pedido) {
        this.dao.insertOrUpdate(pedido);
    }

    public Pedido searchById(Integer id) {
        Pedido pedido = this.cache.get(Integer.toString(id));
        if (pedido != null) return pedido;

        try {
            pedido = this.dao.select(id);
            this.cache.set(Integer.toString(pedido.getIdPedido()), pedido);
        }
        catch(Exception e) { // catch database driver posible exception
            // Handle exception
        }
    }

    public void remove(Integer id) {
        this.cache.remove(Integer.toString(id));
        try {
            this.dao.remove(id)
        }
        catch(Exception e) { // catch database driver posible exception
            // Handle exception
        }
	}
}
