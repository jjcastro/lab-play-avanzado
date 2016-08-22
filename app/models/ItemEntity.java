package models;

/**
 * Created by jg.murillo10 on 20/08/2016.
 */
import javax.persistence.*;
import com.avaje.ebean.Model;

@Entity
@Table(name = "itementity")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "Item")
    private Long id;
    private Long product_id;
    private Long wishlist_id;
    private int quantity;


    public ItemEntity(){
        this.id=null;
        this.product_id=null;
        this.wishlist_id=null;
        this.quantity=-1;

    }

    public ItemEntity(Long id, Long product_id, Long wishlist_id){

        this();
        this.id=id;
        this.product_id=product_id;
        this.wishlist_id=wishlist_id;
    }

    public ItemEntity(Long id, Long product_id, Long wishlist_id, int quantity){

        this();
        this.id=id;
        this.product_id=product_id;
        this.wishlist_id=wishlist_id;
        this.quantity=quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long id) {
        this.product_id = id;
    }
    public Long getWishlist_id() {
        return wishlist_id;
    }

    public void setWishlist_id(Long id) {
        this.wishlist_id = id;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int id) {
        this.quantity = id;
    }
    @Override
    public String toString() {
        return "ItemEntity{" +
                "id=" + id +
                ", product_id='" + product_id + '\'' +
                ", wishlist_id=" + wishlist_id +
                ", quantity=" + quantity +
                '}';
    }
}
