package retsys.client.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Callback;
import retsys.client.helper.LovHandler;
import retsys.client.json.JsonHelper;
import retsys.client.menu.Menu;
import retsys.client.menu.MenuOperationType;
import retsys.client.model.PurchaseOrder;

public class DashboardController extends AnchorPane implements Initializable {

    public static HostServices hostServices;
    @FXML
    private AnchorPane menu;
    @FXML
    private AnchorPane dash;
    public static String pkey = null;
    @FXML
    public Button swapArrow;
    @FXML
    public ListView<String> CustomerList = new ListView();
    @FXML
    public Accordion Accor = new Accordion();
    @FXML
    public AnchorPane MenuAnchor = new AnchorPane();
    @FXML
    private Button btn_go;
    @FXML
    private BorderPane layout;
    @FXML
    private TextField txt_launch_screen;
    @FXML
    private Label lbl_invalid;
    @FXML
    private Button toggleAppMenu;
    @FXML
    private TableColumn id = new TableColumn();
    @FXML
    private AnchorPane taskCommentPane;
    @FXML
    private ListView newTaskComments;
    @FXML
    private TableColumn state = new TableColumn();
    @FXML
    private TableColumn userImage = new TableColumn();
    @FXML
    private TableColumn procId = new TableColumn();
    @FXML
    private TableColumn assignee = new TableColumn();
    @FXML
    private TableColumn description = new TableColumn();
    @FXML
    private TableColumn createTime = new TableColumn();
    @FXML
    private TableColumn dueDate = new TableColumn();
    @FXML
    private TableColumn processName = new TableColumn();
    @FXML
    private TableColumn priority = new TableColumn();
    @FXML
    private TableColumn functionId = new TableColumn();
    @FXML
    private Button refresh = new Button();
    @FXML
    private TableColumn procDefnName = new TableColumn();
    @FXML
    private TableColumn procDefnDesc = new TableColumn();
    @FXML
    private Button procInitiate = new Button();
    @FXML
    private Button openTask = new Button();
    @FXML
    private Label taskId = new Label();
    @FXML
    private Label taskDesc = new Label();
    @FXML
    private Label taskName = new Label();
    @FXML
    private Label createdOn = new Label();
    @FXML
    private Label createdBy = new Label();
    @FXML
    private Label prospectiveCustomer;
    @FXML
    private Label taskStatus;
    @FXML
    private Label taskAction;
    @FXML
    private Label success;
    @FXML
    private Button taskPriority;
    @FXML
    private Button b2;
    @FXML
    private TreeView<Menu> appMenu;
    @FXML
    private TreeView<Menu> treeView;
    @FXML
    private TextField flowName = new TextField();
    @FXML
    private TextField flowOriginator = new TextField();
    @FXML
    private Label modifiedOn = new Label();
    @FXML
    private Label modifiedBy = new Label();
    @FXML
    private TableColumn author = new TableColumn();
    @FXML
    private TableColumn commentDate = new TableColumn();
    @FXML
    private TableColumn comment = new TableColumn();
    @FXML
    private Hyperlink saveProcPaneChanges = new Hyperlink();
    @FXML
    private Hyperlink cancelProcPaneChanges = new Hyperlink();
    @FXML
    private Hyperlink processPaneEdit = new Hyperlink();
    @FXML
    private Hyperlink importantFirst = new Hyperlink();
    @FXML
    private Hyperlink closeDeadline = new Hyperlink();
    @FXML
    private Hyperlink fastFirst = new Hyperlink();

    @FXML
    public ComboBox<String> scorePeriod;

    /* Tabbed feature */
    @FXML
    private TabPane landingTabPane;
    @FXML
    private Tab dashboardTab;
    /* Tabbed feature */
    private ObservableList<String> processSearchItems = FXCollections.observableArrayList();
    /* Toplist */
    @FXML
    private TableColumn temp;
    /* Toplist */
    /* app menu */
    @FXML
    private ListView<String> processes = new ListView<>();
    /* app menu */

    //private PreDemo LoadFunction;
    public static Stage stage;

    @FXML
    public Label today;
    @FXML
    public Label totalScore;
    @FXML
    public Label time;

    @FXML
    public Label remainingTaskCount;

    // launches the flconfig.xml screen on click of button
    // in the header under process search
    @FXML
    public Button launchFLConsole;

    @FXML
    public ListView<PurchaseOrder> pendingPOs;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeNewMenuTree();
        initializePendingPO();

        LocalDate todaysDate = LocalDate.now();
        today.setText(todaysDate.format(DateTimeFormatter.ofPattern("d MMMM y")));

        scheduleTimeRefresh();
    }

    private void scheduleTimeRefresh() {
        TimerTask headerClock = new TimerTask() {

            @Override
            public void run() {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        time.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
                    }
                });
            }
        };

        time.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        long delay = 60 - LocalTime.now().getSecond();

        Timer timerTask = new Timer(true);

        timerTask.schedule(headerClock, delay * 1000, 60000);
    }

    SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");

    private void initializePendingPO() {
        pendingPOs.setCellFactory(new Callback<ListView<PurchaseOrder>, ListCell<PurchaseOrder>>() {

            @Override
            public ListCell<PurchaseOrder> call(ListView<PurchaseOrder> param) {
                ListCell<PurchaseOrder> cell = new ListCell<PurchaseOrder>() {

                    @Override
                    protected void updateItem(PurchaseOrder item, boolean empty) {
                        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.

                        if (!empty && item != null) {
                            Label label1 = new Label("ID:");
                            label1.getStyleClass().add("pendingpo-content-heading");
                            Label label11 = new Label(String.valueOf(item.getId()));
                            label11.getStyleClass().add("pendingpo-content-body");
                            Label label2 = new Label("  Vendor:");
                            label2.getStyleClass().add("pendingpo-content-heading");
                            Label label21 = new Label(item.getVendor().getName());
                            label21.getStyleClass().add("pendingpo-content-body");
                            Label label3 = new Label("  Created on: ");
                            label3.getStyleClass().add("pendingpo-content-heading");
                            Label label31 = new Label(format.format(item.getDate()));
                            label31.getStyleClass().add("pendingpo-content-body");

                            HBox box = new HBox(label1, label11, label2, label21, label3, label31);
                            box.setSpacing(5);

                            setGraphic(box);
                            box.setOnMouseClicked(new EventHandler<MouseEvent>() {

                                @Override
                                public void handle(MouseEvent event) {
                                    StandardController controller = openScreen("/retsys/client/fxml/PurchaseOrderConfirm.fxml", "Purchase Order Confirmation");
                                    controller.init(item);
                                }
                            });
                        } else {
                            setGraphic(null);
                        }
                    }

                };

                return cell;
            }
        });

        TimerTask fetchPendingPO = new TimerTask() {

            @Override
            public void run() {
                try {
                    LovHandler lovHandler = new LovHandler("purchaseorders/pendingpo", null, 0, 10);
                    String response = lovHandler.getSuggestions(null);
                    List<PurchaseOrder> list = (List<PurchaseOrder>) new JsonHelper().convertJsonStringToObject(response, new TypeReference<List<PurchaseOrder>>() {
                    });

                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            pendingPOs.setItems(FXCollections.observableArrayList(list));
                        }
                    });
                } catch (IOException ex) {
                    Logger.getLogger(VendorController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        };

        Timer poThreadTimer = new Timer(true);
        poThreadTimer.scheduleAtFixedRate(fetchPendingPO, new Date(), 50000);
    }

    public void toggleMenuPane() {
        Platform.runLater(new Runnable() {
            public void run() {
                System.out.println("Swap");
                System.out.println("Width:" + menu.getWidth() + "=>" + "Pref:" + menu.getPrefWidth());
                if (menu.isVisible()) {
                    System.out.println("Collapse");
                    menu.setVisible(false);
                    swapArrow.setDefaultButton(true);
                    AnchorPane.setLeftAnchor(dash, 0d);
                } else {
                    System.out.println("Expand");
                    menu.setVisible(true);
                    swapArrow.setDefaultButton(false);
                    AnchorPane.setLeftAnchor(dash, 218d);

                }
            }
        });
    }

    TreeItem cache;

    private void initializeNewMenuTree() {
        TreeItem<Menu> root = new TreeItem<Menu>();
        //root.setValue("");
        treeView.setRoot(root);
        /*String[][] level1 = new String[][]{new String[]{"Project Management", "project"}, new String[]{"Inventory Management", "inventory"}};
         String[][] level2 = new String[][]{new String[]{"Project", "project"}, new String[]{"Product", "product"}, new String[]{"Client", "client"}};
         String[][] level3 = new String[][]{new String[]{"Project Maintainence", "project-maintainence"}, new String[]{"Open Project", "OpenProject"}};
         String[][] level4 = new String[][]{new String[]{"Create Project", "Createproject"}, new String[]{"New Product", "/retsys/client/fxml/Product.fxml"},new String[]{"New Client", "/retsys/client/fxml/Client.fxml"},new String[]{"New Vendor", "/retsys/client/fxml/Vendor.fxml"},new String[]{"New Project", "/retsys/client/fxml/Project.fxml"}};*/

        String[][] level1 = new String[][]{new String[]{"Project Management", "project-management", "1", ""}, new String[]{"Inventory Management", "inventory-management", "2", ""}, new String[]{"Admin", "inventory-management", "30", ""}};
        String[][] level2 = new String[][]{new String[]{"Project", "project", "3", "1"}, new String[]{"Product", "product", "4", "2"}, new String[]{"Client", "client", "5", "1"}, new String[]{"Vendor", "vendor", "9", "2"}, new String[]{"Purchase Order", "purchaseorder", "14", "2"}, new String[]{"Purchase Return", "purchasereturn", "16", "2"}, new String[]{"Purchase Order Confirm", "purchaseorderconfirm", "18", "2"}, new String[]{"Items", "item5", "25", "2"}, new String[]{"Delivery Challan", "deliverychallan", "27", "2"}, new String[]{"User Maintenance", "deliverychallan", "31", "30"}, new String[]{"Reports", "report", "33", "30"}};
        String[][] level3 = new String[][]{new String[]{"Project Maintainence", "/retsys/client/fxml/Project.fxml", "6", "3"}, new String[]{"Product Maintenance", "/retsys/client/fxml/Product.fxml", "7", "4"}, new String[]{"Client Maintenance", "/retsys/client/fxml/Client.fxml", "8", "5"}, new String[]{"Vendor Maintenance", "/retsys/client/fxml/Vendor.fxml", "10", "9"}, new String[]{"Purchase Order Maintenance", "/retsys/client/fxml/PurchaseOrder.fxml", "15", "14"}, new String[]{"Purchase Order Confirmation", "/retsys/client/fxml/PurchaseOrderConfirm.fxml", "15", "18"}, new String[]{"Purchase Return", "/retsys/client/fxml/CreditNote.fxml", "20", "16"}, new String[]{"Item Maintenance", "/retsys/client/fxml/Item.fxml", "26", "25"}, new String[]{"DeliveryChallan", "/retsys/client/fxml/DeliveryChallan.fxml", "28", "27"}, new String[]{"DeliveryChallanReturn", "/retsys/client/fxml/DeliveryChallanReturn.fxml", "29", "27"}, new String[]{"User", "/retsys/client/fxml/User.fxml", "32", "31"}, new String[]{"Site Report", "/retsys/client/fxml/INVENTORY_REPORT.fxml", "34", "33"}, new String[]{"Stock Report", "/retsys/client/fxml/STOCK_REPORT.fxml", "35", "33"}};
        //String[][] level4 = new String[][]{new String[]{"Create Product", "/retsys/client/fxml/Product.fxml","11","7"}, new String[]{"Modify Product", "/retsys/client/fxml/Product.fxml","12","7"}, new String[]{"View Product", "/retsys/client/fxml/Product.fxml","13","7"}, new String[]{"Create Purchase Order", "/retsys/client/fxml/PurchaseOrder.fxml","16","15"},new String[]{"Modify Purchase Order", "/retsys/client/fxml/PurchaseOrder.fxml","17","15"},new String[]{"Create Purchase Order", "/retsys/client/fxml/PurchaseOrder.fxml","17","15"},new String[]{"Create Client", "/retsys/client/fxml/Client.fxml","18","8"},new String[]{"Modify Client ", "/retsys/client/fxml/Client.fxml","19","8"},new String[]{"View Client ", "/retsys/client/fxml/Client.fxml","20","8"},new String[]{"Create Vendor", "/retsys/client/fxml/Vendor.fxml","21","10"},new String[]{"Modify Vendor", "/retsys/client/fxml/Vendor.fxml","22","10"},new String[]{"View Vendor", "/retsys/client/fxml/Vendor.fxml","22","10"},new String[]{"Create Project", "/retsys/client/fxml/Project.fxml","23","6"},new String[]{"Modify Project", "/retsys/client/fxml/Project.fxml","24","6"},new String[]{"View Project", "/retsys/client/fxml/Project.fxml","24","6"},new String[]{"Create Item", "/retsys/client/fxml/Item.fxml","27","26"},new String[]{"Modify Item", "/retsys/client/fxml/Item.fxml","28","26"},new String[]{"View Item", "/retsys/client/fxml/Item.fxml","29","26"}};

        for (String[] l1 : level1) {
            TreeItem<Menu> ti1 = new TreeItem<Menu>();
            Menu m = new Menu(MenuOperationType.NONE, null, 1, l1[0], l1[1]);

            ti1.setValue(m);
            for (String[] l2 : level2) {
                if (l2[3].equals(l1[2])) {
                    TreeItem<Menu> ti2 = new TreeItem<Menu>();
                    Menu m2 = new Menu(MenuOperationType.NONE, null, 2, l2[0], l2[1]);
                    ti2.setValue(m2);
                    for (String[] l3 : level3) {
                        if (l3[3].equals(l2[2])) {
                            TreeItem<Menu> ti3 = new TreeItem<Menu>();
                            Menu m3 = new Menu(MenuOperationType.PROCESS, l3[1], 4, l3[0], l3[1]);
                            ti3.setValue(m3);
                            /*for (String[] l4 : level4) {
                             if(l4[3].equals(l3[2])){
                             TreeItem<Menu> ti4 = new TreeItem<Menu>();
                             Menu m4 = new Menu(MenuOperationType.PROCESS, l4[1], 4, l4[0], l4[1]);
                             cache = ti4;
                             ti4.setValue(m4);
                             ti3.getChildren().add(ti4);
                             }
                             }*/

                            ti2.getChildren().add(ti3);
                        }
                    }
                    ti1.getChildren().add(ti2);
                }
            }
            treeView.getRoot().getChildren().add(ti1);
        }

        treeView.setShowRoot(false);
        treeView.getRoot().setExpanded(true);

        treeView.setCellFactory(new Callback<TreeView<Menu>, TreeCell<Menu>>() {
            @Override
            public TreeCell<Menu> call(TreeView<Menu> p) {
                final TreeCell<Menu> cell = new TreeCell<Menu>() {
                    @Override
                    protected void updateItem(Menu t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {

                            boolean isOpenOperation = getStyleClass().contains("open-operation" + t.getLevel());

                            getStyleClass().clear();
                            getStyleClass().addAll("cell", "indexed-cell", "tree-cell");

                            if (t.isHasOperation() && !isOpenOperation) {
                                getStyleClass().add("open-operation" + t.getLevel());
                                System.out.println("added" + t.getName());
                            } else {
                                getStyleClass().remove("open-operation" + t.getLevel());
                                System.out.println("removed" + t.getName());
                            }

                            getStyleClass().add(t.getStyleClass());
                            getStyleClass().add("level" + t.getLevel());
                            if (t.isHasOperation()) {
                                Platform.runLater(new Runnable() {
                                    public void run() {
                                        setFocused(true);
                                    }
                                });
                            }

                            if (t.getLevel() == 1) {
                                AnchorPane p = new AnchorPane();
                                Label content = new Label(t.getName());
                                Region r = new Region();
                                r.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent t) {
                                        getTreeItem().expandedProperty().set(!getTreeItem().isExpanded());
                                    }
                                });
                                r.getStyleClass().add("arrowx");
                                p.getChildren().addAll(content, r);

                                setLayout(content, 0, -1, 0, 0);
                                setLayout(r, 0, 0, 0, - 1);
                                setGraphic(p);
                            } else {
                                if (t.getLevel() == 4) {
                                    Label content = new Label(t.getName());
                                    content.setUserData(t);
                                    content.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent event) {
                                            Label content = (Label) event.getSource();
                                            Menu m = (Menu) content.getUserData();
                                            if (m.getOperation() == MenuOperationType.PROCESS) {
                                                openScreen(m.getUnit(), ((Label) event.getSource()).getText());
                                            }
                                        }
                                    });
                                    setGraphic(content);
                                } else {
                                    setText(t.getName());
                                }

                            }

                        } else {
                            setGraphic(null);
                            setText(null);
//                            getStyleClass().clear();
                            getStyleClass().addAll("cell", "indexed-cell", "tree-cell");
                        }
                    }
                };
                cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        if (cell.getTreeItem() == null) {
                            return;
                        }
                        cell.getTreeItem().setExpanded(!cell.getTreeItem().isExpanded());

                        if (cell.getTreeItem().isLeaf()) {
//                            for (String s : cell.getStyleClass()) {
//                                System.out.println(s);
//                            }

//                            Platform.runLater(new Runnable() {
//                                @Override
//                                public void run() {
                            cell.getTreeItem().getValue().setHasOperation(
                                    !cell.getTreeItem().getValue().isHasOperation());

                            nofityParentOperation(cell.getTreeItem(),
                                    cell.getTreeItem().getValue().isHasOperation());

                            cell.getTreeView().requestLayout();

                            //                            cell.getTreeItem().getValue().
                            //                                      setHasOperation(!cell.getTreeItem().getValue().isHasOperation());
                            //
//                            cell.updateTreeItem(cell.getTreeItem());
//                                }
//                            });
                        }

                    }
                });
                return cell;

            }
        });

    }

    TreeItem openedCell;

    public int countOperations(TreeItem<retsys.client.menu.Menu> item) {
        int cnt = 0;
        for (TreeItem<retsys.client.menu.Menu> i : item.getChildren()) {
            //System.out.println("::" + i.getValue().getName() + "::" + i.isLeaf());

            if (i.getChildren().size() > 0) {
                cnt += countOperations(i);
            } else if (i.getValue().isHasOperation()) {
                cnt++;
            }

        }

        return cnt;
    }

    public void nofityParentOperation(final TreeItem item, final boolean operation) {

        TreeItem<retsys.client.menu.Menu> i = item.getParent();
        while (i != null && i.getValue() != null) {
            int cnt = countOperations(i);
            if (operation == false && cnt < 1) {
                i.getValue().setHasOperation(false);
            } else if (operation == true) {
                i.getValue().setHasOperation(true);
            }

            i = i.getParent();

        }

    }

    public void setLayout(Node n, double top, double right, double bottom, double left) {
        if (top >= 0) {
            AnchorPane.setTopAnchor(n, top);
        }
        if (left >= 0) {
            AnchorPane.setLeftAnchor(n, left);
        }
        if (right >= 0) {
            AnchorPane.setRightAnchor(n, right);
        }
        if (bottom >= 0) {
            AnchorPane.setBottomAnchor(n, bottom);
        }

    }

    private StandardController openScreen(String screenName, String title) {
        URL baseFxmlLocation = getClass().getResource("/retsys/client/fxml/base-fxml.fxml");
        FXMLLoader baseFxmlLoader = new FXMLLoader(baseFxmlLocation);
        URL location = getClass().getResource(screenName);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root;
        StandardController controller = null;
        try {
            root = (Parent) fxmlLoader.load(location.openStream());
            baseFxmlLoader.setController(fxmlLoader.getController());
            Node baseNode = baseFxmlLoader.load();
            controller = fxmlLoader.getController();
            controller.functionNode.getChildren().add(root);
            Tab funcTab = new Tab();
            funcTab.setClosable(true);

            funcTab.setText(title);

            this.landingTabPane.getTabs().add(funcTab);
            this.landingTabPane.getSelectionModel().select(funcTab);

            funcTab.setContent(baseNode);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return controller;
    }

}
