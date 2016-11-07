package au.edu.uts.aip.domain.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * A data model class to access data in the Category table in the database. A Category object
 * describes information of book category
 *
 * Category entity has properties id and category name All the attributes of this model class are
 * private attributes and all of them have relative get and set functions in order to access the
 * attributes
 *
 * @author
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Category.find", query = "SELECT c from Category c where c.categoryName=:name")
})
public class Category implements Serializable {

    /**
     * the id of category
     */
    private int Id;
    /**
     * the name of the category
     */
    private String categoryName;

    /**
     * {@link Category#id} data type: Integer return an int value
     */
    @Id
    @GeneratedValue
    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    /**
     * {@link Category#categoryName}
     */
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
