/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.menu;

/**
 *
 * @author Narayan G. Maharjan <me@ngopal.com.np>
 */
public class Menu {

    public Menu(MenuOperationType operation, String unit, int level, String name, String style) {
        this.level = level;
        this.name = name;
        this.unit = unit;
        this.styleClass = style;
        this.operation = operation;
    }

    private MenuOperationType operation;

    private String unit;

    private String styleClass;

    private boolean hasOperation;

    private int level;

    private String name;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHasOperation() {
        return hasOperation;
    }

    public void setHasOperation(boolean hasOperation) {
        this.hasOperation = hasOperation;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public MenuOperationType getOperation() {
        return operation;
    }

    public String getUnit() {
        return unit;
    }

}
