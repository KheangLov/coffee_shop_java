/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffee_shop_java.project;

import coffee_shop_java.project.Helper.AppHelper;
import coffee_shop_java.project.Model.Branch;
import coffee_shop_java.project.Model.Import;
import coffee_shop_java.project.Model.ImportDetail;
import coffee_shop_java.project.Model.Stock;
import coffee_shop_java.project.Model.StockCategory;
import coffee_shop_java.project.Model.Company;
import coffee_shop_java.project.Model.Product;
import coffee_shop_java.project.Model.ProductCategory;
import coffee_shop_java.project.Model.ProductVariant;
import coffee_shop_java.project.Model.Supplier;
import coffee_shop_java.project.Model.User;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


/**
 *
 * @author LYOKO
 */
public class Main_Menu extends javax.swing.JFrame {
    boolean com = false;
    boolean bra = false;
    boolean prod = false;
    boolean stocks = false;
    boolean staffs = false;
    boolean user = false;
    boolean dash = false;
    boolean sup = false;
    
    /**
     * Creates new form Main_Menu
     */
    
    private int userId;
    private int roleId;
    private int stockCateId;
    private int comId;
    private int branchId;
    private int supId;
    private int proCateId;
    private String companyNames = "";
    
    StockCategory myStockCate = new StockCategory();
    Stock myStock = new Stock();
    Import myImport = new Import();
    ImportDetail myImpDetail = new ImportDetail();
    Company myCompany = new Company();
    Branch myBranch = new Branch();    
    Supplier mySupplier = new Supplier();
    ProductCategory myProCate = new ProductCategory();
    Product myProduct = new Product();
    ProductVariant myProVar = new ProductVariant();
    
    public Main_Menu(int uId, int rId) {
        initComponents();
        userId = uId;
        roleId = rId;
        this.setExtendedState(Main_Menu.MAXIMIZED_BOTH);            
        int i = 0;
        for(Company c : getComList()) {
            companyNames += "'" + c.getName() + "'";
            i++;
            if(i < getComList().size())
                companyNames += ", ";
        }
    }

    public Main_Menu() {
        initComponents();
    }
    
    public void showUsers(ArrayList<User> list) {
        userTbl.setModel(new DefaultTableModel(
            null, 
            new String[]{
                "#", 
                "Name", 
                "Email", 
                "Gender", 
                "Status", 
                "Role", 
                "id"
            }
        ));
        DefaultTableModel model = (DefaultTableModel) userTbl.getModel();
        Object[] rows = new Object[7];
        for(int i=0; i<list.size(); i++){
            rows[0] = list.get(i).getTblId();
            rows[1] = list.get(i).getFullname();
            rows[2] = list.get(i).getEmail();
            rows[3] = list.get(i).getGender();
            rows[4] = list.get(i).getStatus();
            rows[5] = list.get(i).getRole_name();
            rows[6] = list.get(i).getId();
            model.addRow(rows);
        }
        AppHelper.setColWidth(userTbl, 6, 0);
        AppHelper.setColWidth(userTbl, 0, 50);
    }
    
    public ArrayList<User> getAllUsers() {
        ArrayList<User> list = new ArrayList<>();
        if(AppHelper.currentUserCan(roleId, "users", "read")) {
            String sql = "SELECT `users`.*, `roles`.`name` AS role_name FROM `users` "
                + "INNER JOIN `roles` ON `users`.`role_id` = `roles`.`id`";
            try {
                ResultSet rs = AppHelper.selectQuery(sql);
                User user;
                int i = 0;
                while(rs.next()){
                    i++;
                    user = new User(
                        i,
                        rs.getString("fullname"),
                        rs.getString("email"),
                        rs.getString("gender"),
                        rs.getString("status"),
                        rs.getString("role_name"),
                        rs.getInt("id")
                    );
                    list.add(user);
                }      
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
        return list;
    }
    
    public ArrayList<User> searchUser(String text) {
        ArrayList<User> list = new ArrayList<>();
        if(AppHelper.currentUserCan(roleId, "users", "read")) {
            String sql = "SELECT `users`.*, `roles`.`name` AS role_name FROM `users` "
                + "INNER JOIN `roles` ON `users`.`role_id` = `roles`.`id` "
                + "WHERE LOWER(`users`.`fullname`) LIKE '%" + text + "%'";
            try {
                ResultSet rs = AppHelper.selectQuery(sql);
                User user;
                int i = 0;
                while(rs.next()){
                    i++;
                    user = new User(
                        i,
                        rs.getString("fullname"),
                        rs.getString("email"),
                        rs.getString("gender"),
                        rs.getString("status"),
                        rs.getString("role_name"),
                        rs.getInt("id")
                    );
                    list.add(user);
                } 
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
        return list;
    }
    
    public void showStockCate(ArrayList<StockCategory> list) {
        tblStockCate.setModel(new DefaultTableModel(
            null, 
            new String[]{
                "#", 
                "Name", 
                "Description",
                "id"
            }
        ));
        DefaultTableModel model = (DefaultTableModel) tblStockCate.getModel();
        Object[] rows = new Object[4];
        for(int i=0; i<list.size(); i++){
            rows[0] = list.get(i).getTblId();
            rows[1] = list.get(i).getName();
            rows[2] = list.get(i).getDescription();
            rows[3] = list.get(i).getId();
            model.addRow(rows);
        }
        AppHelper.setColWidth(tblStockCate, 3, 0);
        AppHelper.setColWidth(tblStockCate, 0, 50);
    }
    
    public ArrayList<StockCategory> getAllStockCate() {
        ArrayList<StockCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM `stock_categories`";
        try {
            ResultSet rs = AppHelper.selectQuery(sql);
            StockCategory stockCate;
            int i = 0;
            while(rs.next()){
                i++;
                stockCate = new StockCategory(
                    i,
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("id")
                );
                list.add(stockCate);
            } 
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return list;
    }
    
    public void showStocks(ArrayList<Stock> list) {
        tblStock.setModel(new DefaultTableModel(
            null, 
            new String[]{
                "#", 
                "Name", 
                "Quantity",
                "Measure Unit",
                "Stock Category",
                "id"
            }
        ));
        DefaultTableModel model = (DefaultTableModel) tblStock.getModel();
        Object[] rows = new Object[6];
        for(int i=0; i<list.size(); i++){
            rows[0] = list.get(i).getTblId();
            rows[1] = list.get(i).getName();
            rows[2] = list.get(i).getQty();
            rows[3] = list.get(i).getMeasureUnit();
            rows[4] = AppHelper.toCapitalize(list.get(i).getStockCateName());
            rows[5] = list.get(i).getId();
            model.addRow(rows);
        }
        AppHelper.setColWidth(tblStock, 5, 0);
        AppHelper.setColWidth(tblStock, 0, 50);
    }
    
    public ArrayList<Stock> getAllStocks() {
        ArrayList<Stock> list = new ArrayList<>();
        if(AppHelper.currentUserCan(roleId, "stocks", "read")) {
            String sql = "SELECT `stocks`.*, `stock_categories`.`name` AS stock_cate_name "
                    + "FROM `stocks` "
                    + "INNER JOIN `stock_categories` "
                    + "ON `stocks`.`stock_category_id` = `stock_categories`.`id`";
            try {
                ResultSet rs = AppHelper.selectQuery(sql);
                Stock stock;
                int i = 0;            
                while(rs.next()) {
                    i++;
                    stock = new Stock(
                        i,
                        rs.getString("name"),
                        rs.getDouble("qty"),
                        rs.getString("measure_unit"),
                        rs.getString("stock_cate_name"),
                        rs.getInt("id")
                    );
                    list.add(stock);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
        return list;
    }
    
    public ArrayList<Company> getComList(){
        ArrayList<Company> list = new ArrayList<>();
        if(AppHelper.currentUserCan(roleId, "companies", "read")) {
            String sql = "SELECT companies.*, users.fullname as username "
                + "FROM companies "
                + "INNER JOIN users ON companies.user_id = users.id "
                + "WHERE companies.user_id = ?";
            try {
                ResultSet rs = AppHelper.selectQuery(sql, userId);
                Company userCom;
                int i = 0;
                while(rs.next()){
                    i++;
                    userCom = new Company(
                        i,
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("username"),
                        rs.getInt("id")
                    );
                    list.add(userCom);
                } 
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
        return list;
    }
    
    public void showCompanies(ArrayList<Company> list){
        tblCom.setModel(new DefaultTableModel(
            null,
            new String[]{
                "#",
                "Name",
                "Address",
                "Email",
                "Phone",
                "User",
                "id"
            }
        ));
        DefaultTableModel model = (DefaultTableModel) tblCom.getModel();
        Object[] row = new Object[7];
        for(int i=0; i<list.size(); i++){
            row[0] = list.get(i).getTblId();
            row[1] = list.get(i).getName();
            row[2] = list.get(i).getAddress();
            row[3] = list.get(i).getEmail();
            row[4] = list.get(i).getPhone();
            row[5] = list.get(i).getComName();
            row[6] = list.get(i).getId();
            model.addRow(row);
        }
        AppHelper.setColWidth(tblCom, 6, 0);
        AppHelper.setColWidth(tblCom, 0, 50);
    }
    
    public ArrayList<Company> searchCom(String text) {
        ArrayList<Company> list = new ArrayList<>();
        if(AppHelper.currentUserCan(roleId, "companies", "read")) {
            String sql = "SELECT companies.*, users.fullname as username "
                    + "FROM companies "
                    + "INNER JOIN users ON companies.user_id = users.id "
                    + "WHERE companies.user_id = ? "
                    + "AND LOWER(companies.name) LIKE '%" + text + "%'";
            try {
                ResultSet rs = AppHelper.selectQuery(sql, userId);
                Company company;
                int i = 0;
                while(rs.next()){
                    i++;
                    company = new Company(
                        i,
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("username"),
                        rs.getInt("id")
                    );
                    list.add(company);
                } 
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
        return list;
    }
    
    public void showBranches(ArrayList<Branch> list) {
        tblBranch.setModel(new DefaultTableModel(
            null,
            new String[]{
                "#",
                "Name",
                "Address",
                "Email",
                "Phone",
                "Company",
                "id"
            }
        ));
        DefaultTableModel model = (DefaultTableModel) tblBranch.getModel();
        Object[] row = new Object[7];
        for(int i=0; i<list.size(); i++){
            row[0] = list.get(i).getTblId();
            row[1] = list.get(i).getName();
            row[2] = list.get(i).getAddress();
            row[3] = list.get(i).getEmail();
            row[4] = list.get(i).getPhone();
            row[5] = list.get(i).getComName();
            row[6] = list.get(i).getId();
            model.addRow(row);
        }
        AppHelper.setColWidth(tblBranch, 6, 0);
        AppHelper.setColWidth(tblBranch, 0, 50);
    }
    
    public ArrayList<Branch> getBranchList() {
        ArrayList<Branch> list = new ArrayList<>();
        if(AppHelper.currentUserCan(roleId, "branches", "read")) {
            String sql = "SELECT `branches`.*, `companies`.`name` AS company_name FROM `branches` "
                + "INNER JOIN `companies` ON `branches`.`company_id` = `companies`.`id` "
                + "WHERE LOWER(`companies`.`name`) IN (" + companyNames + ")";
            ResultSet rs = AppHelper.selectQuery(sql);
            Branch branch;
            int tblId = 0;
            try {
                while(rs.next()) {
                    tblId++;
                    branch = new Branch(
                        tblId,
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("company_name"),
                        rs.getInt("id")
                    );
                    list.add(branch);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Main_Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
    
    public ArrayList<Branch> searchBranch(String text) {
        ArrayList<Branch> list = new ArrayList<>();
        if(AppHelper.currentUserCan(roleId, "branches", "read")) {
            String sql = "SELECT `branches`.*, `companies`.`name` AS company_name FROM `branches` "
                + "INNER JOIN `companies` ON `branches`.`company_id` = `companies`.`id` "
                + "WHERE LOWER(`companies`.`name`) IN (" + companyNames + ") "
                + "AND LOWER(`branches`.`name`) LIKE '%" + text + "%'";
            ResultSet rs = AppHelper.selectQuery(sql);
            Branch branch;
            int tblId = 0;
            try {
                while(rs.next()) {
                    tblId++;
                    branch = new Branch(
                        tblId,
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("company_name"),
                        rs.getInt("id")
                    );
                    list.add(branch);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Main_Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
    
    public void showSup(ArrayList<Supplier> list) {
        tblSup.setModel(new DefaultTableModel(
            null,
            new String[]{
                "#",
                "Name",
                "Address",
                "Email",
                "Phone",
                "Company",
                "Branch",
                "id"
            }
        ));
        DefaultTableModel model = (DefaultTableModel) tblSup.getModel();
        Object[] row = new Object[8];
        for(int i=0; i<list.size(); i++){
            row[0] = list.get(i).getTblId();
            row[1] = list.get(i).getName();
            row[2] = list.get(i).getAddress();
            row[3] = list.get(i).getEmail();
            row[4] = list.get(i).getPhone();
            row[5] = list.get(i).getComName();
            row[6] = list.get(i).getBranchName();
            row[7] = list.get(i).getId();
            model.addRow(row);
        }
        AppHelper.setColWidth(tblSup, 7, 0);
        AppHelper.setColWidth(tblSup, 0, 50);
    }
    
    public ArrayList<Supplier> getSupList() {
        ArrayList<Supplier> list = new ArrayList<>();
        if(AppHelper.currentUserCan(roleId, "suppliers", "read")) {       
            String sql = "SELECT `suppliers`.*, "
                + "`companies`.`name` AS company_name, "
                + "`branches`.`name` AS branch_name "
                + "FROM `suppliers` "
                + "INNER JOIN `companies` "
                + "ON `suppliers`.`company_id` = `companies`.`id` "
                + "INNER JOIN `branches` "
                + "ON `suppliers`.`branch_id` = `branches`.`id` "
                + "WHERE LOWER(`companies`.`name`) IN (" + companyNames + ")";
            ResultSet rs = AppHelper.selectQuery(sql);
            Supplier supplier;
            int tblId = 0;
            try {
                while(rs.next()) {
                    tblId++;
                    supplier = new Supplier(
                        tblId,
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("company_name"),
                        rs.getString("branch_name"),
                        rs.getInt("id")
                    );
                    list.add(supplier);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Main_Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
    
    public ArrayList<Supplier> searchSup(String text) {
        ArrayList<Supplier> list = new ArrayList<>();
        if(AppHelper.currentUserCan(roleId, "suppliers", "read")) {
            String sql = "SELECT `suppliers`.*, "
                + "`companies`.`name` AS company_name, "
                + "`branches`.`name` AS branch_name "
                + "FROM `suppliers` "
                + "INNER JOIN `companies` "
                + "ON `suppliers`.`company_id` = `companies`.`id` "
                + "INNER JOIN `branches` "
                + "ON `suppliers`.`branch_id` = `branches`.`id` "
                + "WHERE LOWER(`companies`.`name`) IN (" + companyNames + ") "
                + "AND LOWER(`branches`.`name`) LIKE '%" + text + "%'";
            ResultSet rs = AppHelper.selectQuery(sql);
            Supplier supplier;
            int tblId = 0;
            try {
                while(rs.next()) {
                    tblId++;
                    supplier = new Supplier(
                        tblId,
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("company_name"),
                        rs.getString("branch_name"),
                        rs.getInt("id")
                    );
                    list.add(supplier);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Main_Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
    
    public void showProCate(ArrayList<ProductCategory> list) {
        tblProCate.setModel(new DefaultTableModel(
            null, 
            new String[]{
                "#", 
                "Name", 
                "Description",
                "id"
            }
        ));
        DefaultTableModel model = (DefaultTableModel) tblProCate.getModel();
        Object[] rows = new Object[4];
        for(int i=0; i<list.size(); i++){
            rows[0] = list.get(i).getTblId();
            rows[1] = list.get(i).getName();
            rows[2] = list.get(i).getDescription();
            rows[3] = list.get(i).getId();
            model.addRow(rows);
        }
        AppHelper.setColWidth(tblProCate, 3, 0);
        AppHelper.setColWidth(tblProCate, 0, 50);
    }
    
    public ArrayList<ProductCategory> getAllProCate() {
        ArrayList<ProductCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM `product_categories`";
        try {
            ResultSet rs = AppHelper.selectQuery(sql);
            ProductCategory proCate;
            int i = 0;
            while(rs.next()){
                i++;
                proCate = new ProductCategory(
                    i,
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("id")
                );
                list.add(proCate);
            } 
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return list;
    }
    
    public void showProductVar(ArrayList<ProductVariant> list) {
        tblProVar.setModel(new DefaultTableModel(
            null, 
            new String[]{
                "#", 
                "Name", 
                "Size",
                "Price",
                "id"
            }
        ));
        DefaultTableModel model = (DefaultTableModel) tblProVar.getModel();
        Object[] rows = new Object[5];
        for(int i=0; i<list.size(); i++){
            rows[0] = list.get(i).getTblId();
            rows[1] = list.get(i).getProName();
            rows[2] = list.get(i).getSize();
            rows[3] = list.get(i).getSellingPrice();
            rows[4] = list.get(i).getId();
            model.addRow(rows);
        }
        AppHelper.setColWidth(tblProVar, 4, 0);
        AppHelper.setColWidth(tblProVar, 0, 50);
    }
    
    public ArrayList<ProductVariant> getAllProductVars() {
        ArrayList<ProductVariant> list = new ArrayList<>();
        if(AppHelper.currentUserCan(roleId, "products", "read")) {
            String sql = "SELECT * FROM `product_variants` "
                + "INNER JOIN `products` "
                + "ON `product_variants`.`product_id` = `products`.`id` "
                + "WHERE `products`.`user_id` = ?";
            try {
                ResultSet rs = AppHelper.selectQuery(sql, userId);
                ProductVariant product;
                int i = 0;            
                while(rs.next()) {
                    i++;
                    product = new ProductVariant(
                        i,
                        rs.getString("name"),
                        rs.getString("size"),
                        rs.getDouble("selling_price"),
                        rs.getInt("id")
                    );
                    list.add(product);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
        return list;
    }
    
    public void showProduct(ArrayList<Product> list) {
        tblProduct.setModel(new DefaultTableModel(
            null, 
            new String[]{
                "#", 
                "Name", 
                "Category",
                "id"
            }
        ));
        DefaultTableModel model = (DefaultTableModel) tblProduct.getModel();
        Object[] rows = new Object[4];
        for(int i=0; i<list.size(); i++){
            rows[0] = list.get(i).getTblId();
            rows[1] = list.get(i).getName();
            rows[2] = list.get(i).getProCate();
            rows[3] = list.get(i).getId();
            model.addRow(rows);
        }
        AppHelper.setColWidth(tblProduct, 3, 0);
        AppHelper.setColWidth(tblProduct, 0, 50);
    }
    
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> list = new ArrayList<>();
        if(AppHelper.currentUserCan(roleId, "products", "read")) {
            String sql = "SELECT products.*, "
                + "product_categories.name AS procate_name "
                + "FROM products "
                + "INNER JOIN product_categories "
                + "ON products.product_category_id = product_categories.id "
                + "WHERE products.user_id = ?";
            try {
                ResultSet rs = AppHelper.selectQuery(sql, userId);
                Product product;
                int i = 0;            
                while(rs.next()) {
                    i++;
                    product = new Product(
                        i,
                        rs.getString("name"),
                        rs.getString("procate_name"),
                        rs.getInt("id")
                    );
                    list.add(product);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
        return list;
    }
  
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JPanel();
        sidebar = new javax.swing.JPanel();
        comLabel = new javax.swing.JPanel();
        company = new javax.swing.JLabel();
        comIcon = new javax.swing.JLabel();
        branchLabel = new javax.swing.JPanel();
        branch = new javax.swing.JLabel();
        branchIcon = new javax.swing.JLabel();
        proLabel = new javax.swing.JPanel();
        pro = new javax.swing.JLabel();
        proIcon = new javax.swing.JLabel();
        staffLabel = new javax.swing.JPanel();
        staff = new javax.swing.JLabel();
        staffIcon = new javax.swing.JLabel();
        stockLabel = new javax.swing.JPanel();
        stockIcon = new javax.swing.JLabel();
        stock = new javax.swing.JLabel();
        comLayer = new javax.swing.JLayeredPane();
        comBlink = new javax.swing.JPanel();
        comActive = new javax.swing.JPanel();
        braLayer = new javax.swing.JLayeredPane();
        braBlink = new javax.swing.JPanel();
        braActive = new javax.swing.JPanel();
        proLayer = new javax.swing.JLayeredPane();
        proActive = new javax.swing.JPanel();
        proBlink = new javax.swing.JPanel();
        staffLayer = new javax.swing.JLayeredPane();
        staffBlink = new javax.swing.JPanel();
        staffActive = new javax.swing.JPanel();
        stockLayer = new javax.swing.JLayeredPane();
        stockBlink = new javax.swing.JPanel();
        stockActive = new javax.swing.JPanel();
        company1 = new javax.swing.JLabel();
        company2 = new javax.swing.JLabel();
        userPnl = new javax.swing.JPanel();
        userLbl = new javax.swing.JLabel();
        userIcon = new javax.swing.JLabel();
        userLayp = new javax.swing.JLayeredPane();
        userBlink = new javax.swing.JPanel();
        userActive = new javax.swing.JPanel();
        dashPnl = new javax.swing.JPanel();
        dashLbl = new javax.swing.JLabel();
        dashIcon = new javax.swing.JLabel();
        dashLayp = new javax.swing.JLayeredPane();
        dashBlink = new javax.swing.JPanel();
        dashActive = new javax.swing.JPanel();
        btnLogout = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        supplierPnl = new javax.swing.JPanel();
        stockIcon1 = new javax.swing.JLabel();
        stock1 = new javax.swing.JLabel();
        supplierLayp = new javax.swing.JLayeredPane();
        supplierBlink = new javax.swing.JPanel();
        supplierActive = new javax.swing.JPanel();
        dynamicPanel = new javax.swing.JLayeredPane();
        comPanel = new javax.swing.JPanel();
        dynamicComPnl = new javax.swing.JLayeredPane();
        pnlComList = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblCom = new javax.swing.JTable();
        lblSearch = new javax.swing.JLabel();
        txtSearchCom = new javax.swing.JTextField();
        lineSearch = new javax.swing.JPanel();
        pnlComAction = new javax.swing.JPanel();
        lblComAction = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        txtComName = new javax.swing.JTextField();
        LineName = new javax.swing.JPanel();
        btnCom = new javax.swing.JPanel();
        lblComBtn = new javax.swing.JLabel();
        LineEmail = new javax.swing.JPanel();
        lbEmail = new javax.swing.JLabel();
        txtComEmail = new javax.swing.JTextField();
        LinePhone = new javax.swing.JPanel();
        lblPhone = new javax.swing.JLabel();
        txtComPhone = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtComAddres = new javax.swing.JTextArea();
        LineTxtAddress = new javax.swing.JPanel();
        lblAddresss = new javax.swing.JLabel();
        btnComAdd = new javax.swing.JPanel();
        btnUserIconComAdd = new javax.swing.JLabel();
        btnUserLblComAdd = new javax.swing.JLabel();
        btnComEdit = new javax.swing.JPanel();
        btnUserIconComEdit = new javax.swing.JLabel();
        btnUserLblComEdit = new javax.swing.JLabel();
        btnComDel = new javax.swing.JPanel();
        btnUserIconDel = new javax.swing.JLabel();
        btnUserLblDel = new javax.swing.JLabel();
        btnComList = new javax.swing.JPanel();
        btnUserIconComList = new javax.swing.JLabel();
        btnUserLblComList = new javax.swing.JLabel();
        branchPanel = new javax.swing.JPanel();
        dynamicBranchPnl = new javax.swing.JLayeredPane();
        pnlBranchList = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblBranch = new javax.swing.JTable();
        lblSearch1 = new javax.swing.JLabel();
        txtSearchBranch = new javax.swing.JTextField();
        lineSearch1 = new javax.swing.JPanel();
        pnlBranchAction = new javax.swing.JPanel();
        lblBranchAction = new javax.swing.JLabel();
        lblName1 = new javax.swing.JLabel();
        txtBranchName = new javax.swing.JTextField();
        LineName1 = new javax.swing.JPanel();
        btnBranch = new javax.swing.JPanel();
        lblBranchBtn = new javax.swing.JLabel();
        LineEmail1 = new javax.swing.JPanel();
        lbEmail1 = new javax.swing.JLabel();
        txtBranchPhone = new javax.swing.JTextField();
        LinePhone1 = new javax.swing.JPanel();
        lblPhone1 = new javax.swing.JLabel();
        txtBranchEmail = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtBranchAddr = new javax.swing.JTextArea();
        LineTxtAddress1 = new javax.swing.JPanel();
        lblAddresss1 = new javax.swing.JLabel();
        cbBranchCom = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        lblName2 = new javax.swing.JLabel();
        btnBranchAdd = new javax.swing.JPanel();
        btnUserIconComAdd1 = new javax.swing.JLabel();
        btnUserLblComAdd1 = new javax.swing.JLabel();
        btnBranchEdit = new javax.swing.JPanel();
        btnUserIconComEdit1 = new javax.swing.JLabel();
        btnUserLblComEdit1 = new javax.swing.JLabel();
        btnBranchDel = new javax.swing.JPanel();
        btnUserIconDel1 = new javax.swing.JLabel();
        btnUserLblDel1 = new javax.swing.JLabel();
        btnBranchList = new javax.swing.JPanel();
        btnUserIconComList1 = new javax.swing.JLabel();
        btnUserLblComList1 = new javax.swing.JLabel();
        proPanel = new javax.swing.JPanel();
        dynamicProPane = new javax.swing.JLayeredPane();
        pnlProList = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblPro = new javax.swing.JTable();
        jLabel31 = new javax.swing.JLabel();
        txtSearchPro = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        pnlPro = new javax.swing.JPanel();
        lblProAction = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtProName = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        cbProCate = new javax.swing.JComboBox<>();
        jPanel23 = new javax.swing.JPanel();
        btnPro = new javax.swing.JPanel();
        lblProBtn = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        cbProBranch = new javax.swing.JComboBox<>();
        jPanel26 = new javax.swing.JPanel();
        txtProDesc = new javax.swing.JTextField();
        jPanel34 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel44 = new javax.swing.JLabel();
        txtProSearch = new javax.swing.JTextField();
        jPanel28 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tblProduct = new javax.swing.JTable();
        jLabel21 = new javax.swing.JLabel();
        btnProDel = new javax.swing.JPanel();
        lblProVarBtn3 = new javax.swing.JLabel();
        btnProEdit = new javax.swing.JPanel();
        lblProVarBtn4 = new javax.swing.JLabel();
        pnlProCate = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        txtProCateName = new javax.swing.JTextField();
        jPanel31 = new javax.swing.JPanel();
        btnDelProCate = new javax.swing.JPanel();
        lblAdd5 = new javax.swing.JLabel();
        txtProCateDesc = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblProCate = new javax.swing.JTable();
        jLabel35 = new javax.swing.JLabel();
        txtSearchProCate = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel36 = new javax.swing.JLabel();
        btnAddProCate = new javax.swing.JPanel();
        lblProCateAdd = new javax.swing.JLabel();
        btnEditProCate = new javax.swing.JPanel();
        lblAdd8 = new javax.swing.JLabel();
        pnlProVar = new javax.swing.JPanel();
        lblProVarAction = new javax.swing.JLabel();
        btnProVar = new javax.swing.JPanel();
        lblProVarBtn = new javax.swing.JLabel();
        txtProVarPrice = new javax.swing.JTextField();
        jPanel25 = new javax.swing.JPanel();
        txtProVarSellingPrice = new javax.swing.JTextField();
        jPanel35 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        cbProVarSize = new javax.swing.JComboBox<>();
        jLabel41 = new javax.swing.JLabel();
        cbProVarProduct = new javax.swing.JComboBox<>();
        jPanel36 = new javax.swing.JPanel();
        jPanel37 = new javax.swing.JPanel();
        btnProVarImg = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jDesktopPane2 = new javax.swing.JDesktopPane();
        proImg = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jScrollPane12 = new javax.swing.JScrollPane();
        tblProVar = new javax.swing.JTable();
        btnProVarDel = new javax.swing.JPanel();
        lblProVarBtn1 = new javax.swing.JLabel();
        btnProVarEdit = new javax.swing.JPanel();
        lblProVarBtn2 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        txtProVarSearch = new javax.swing.JTextField();
        jPanel27 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        btnProductStock = new javax.swing.JPanel();
        lblProVarBtn5 = new javax.swing.JLabel();
        btnProductVar = new javax.swing.JPanel();
        btnUserIcon8 = new javax.swing.JLabel();
        btnUserLbl8 = new javax.swing.JLabel();
        btnProduct = new javax.swing.JPanel();
        btnUserIcon5 = new javax.swing.JLabel();
        btnUserLbl5 = new javax.swing.JLabel();
        btnProductCate = new javax.swing.JPanel();
        btnUserIcon12 = new javax.swing.JLabel();
        btnUserLbl12 = new javax.swing.JLabel();
        btnProList = new javax.swing.JPanel();
        btnUserIcon13 = new javax.swing.JLabel();
        btnUserLbl13 = new javax.swing.JLabel();
        btnStockPro = new javax.swing.JPanel();
        btnRoleIcon4 = new javax.swing.JLabel();
        btnRoleLbl3 = new javax.swing.JLabel();
        staffPanel = new javax.swing.JPanel();
        userPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        userTbl = new javax.swing.JTable();
        btnAddUser = new javax.swing.JPanel();
        btnUserIcon = new javax.swing.JLabel();
        btnUserLbl = new javax.swing.JLabel();
        btnRole = new javax.swing.JPanel();
        btnRoleIcon = new javax.swing.JLabel();
        btnRoleLbl = new javax.swing.JLabel();
        btnPermission = new javax.swing.JPanel();
        btnRoleIcon1 = new javax.swing.JLabel();
        btnRoleLbl1 = new javax.swing.JLabel();
        btnEditUser = new javax.swing.JPanel();
        btnUserIcon1 = new javax.swing.JLabel();
        btnUserLbl1 = new javax.swing.JLabel();
        btnChangePass = new javax.swing.JPanel();
        btnUserIcon2 = new javax.swing.JLabel();
        btnUserLbl2 = new javax.swing.JLabel();
        btnDelUser = new javax.swing.JPanel();
        btnUserIcon3 = new javax.swing.JLabel();
        btnUserLbl3 = new javax.swing.JLabel();
        btnUserRefresh = new javax.swing.JPanel();
        btnRoleIcon2 = new javax.swing.JLabel();
        txtSearchUser = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        dashPanel = new javax.swing.JPanel();
        stockPanel = new javax.swing.JPanel();
        dynamicStockPane = new javax.swing.JLayeredPane();
        pnlStockImport = new javax.swing.JPanel();
        lblStockAction = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtStockName = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        dpStockExpired = new com.toedter.calendar.JDateChooser();
        jPanel10 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtStockQty = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        cbStockCate = new javax.swing.JComboBox<>();
        jPanel12 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        cbStockMeasure = new javax.swing.JComboBox<>();
        jPanel13 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtStockAlertQty = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtStockPrice = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        cbStockCompany = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        cbStockBranch = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        cbStockSupplier = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        btnStock = new javax.swing.JPanel();
        lblStockBtn = new javax.swing.JLabel();
        pnlStockList = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblStock = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txtSearchStock = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        pnlStockCate = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtStockCateName = new javax.swing.JTextField();
        jPanel20 = new javax.swing.JPanel();
        btnDelStockCate = new javax.swing.JPanel();
        lblAdd4 = new javax.swing.JLabel();
        txtStockCateDesc = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblStockCate = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        txtSearchStockCate = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        btnAddStockCate = new javax.swing.JPanel();
        lblStockCateAdd = new javax.swing.JLabel();
        btnEditStockCate = new javax.swing.JPanel();
        lblAdd7 = new javax.swing.JLabel();
        btnStockEdit = new javax.swing.JPanel();
        btnUserIcon6 = new javax.swing.JLabel();
        btnUserLbl6 = new javax.swing.JLabel();
        btnStockImport = new javax.swing.JPanel();
        btnUserIcon4 = new javax.swing.JLabel();
        btnUserLbl4 = new javax.swing.JLabel();
        btnStockDel = new javax.swing.JPanel();
        btnUserIcon7 = new javax.swing.JLabel();
        btnUserLbl7 = new javax.swing.JLabel();
        btnStockCate = new javax.swing.JPanel();
        btnUserIcon9 = new javax.swing.JLabel();
        btnUserLbl9 = new javax.swing.JLabel();
        btnStockList = new javax.swing.JPanel();
        btnUserIcon11 = new javax.swing.JLabel();
        btnUserLbl11 = new javax.swing.JLabel();
        supplierPanel = new javax.swing.JPanel();
        dynamicSupPnl = new javax.swing.JLayeredPane();
        pnlSupList = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblSup = new javax.swing.JTable();
        lblSearch2 = new javax.swing.JLabel();
        txtSearchSup = new javax.swing.JTextField();
        lineSearch2 = new javax.swing.JPanel();
        pnlSupAction = new javax.swing.JPanel();
        lblSupAction = new javax.swing.JLabel();
        lblName3 = new javax.swing.JLabel();
        txtSupName = new javax.swing.JTextField();
        LineName2 = new javax.swing.JPanel();
        btnSup = new javax.swing.JPanel();
        lblSupBtn = new javax.swing.JLabel();
        LineEmail2 = new javax.swing.JPanel();
        lbEmail2 = new javax.swing.JLabel();
        txtSupEmail = new javax.swing.JTextField();
        LinePhone2 = new javax.swing.JPanel();
        lblPhone2 = new javax.swing.JLabel();
        txtSupPhone = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        txtSupAddr = new javax.swing.JTextArea();
        LineTxtAddress2 = new javax.swing.JPanel();
        lblAddresss2 = new javax.swing.JLabel();
        lblName5 = new javax.swing.JLabel();
        LineName4 = new javax.swing.JPanel();
        cbSupBranch = new javax.swing.JComboBox<>();
        btnSupAdd = new javax.swing.JPanel();
        btnUserIconComAdd2 = new javax.swing.JLabel();
        btnUserLblComAdd2 = new javax.swing.JLabel();
        btnSupEdit = new javax.swing.JPanel();
        btnUserIconComEdit2 = new javax.swing.JLabel();
        btnUserLblComEdit2 = new javax.swing.JLabel();
        btnSupDel = new javax.swing.JPanel();
        btnUserIconDel2 = new javax.swing.JLabel();
        btnUserLblDel2 = new javax.swing.JLabel();
        btnSupList = new javax.swing.JPanel();
        btnUserIconComList2 = new javax.swing.JLabel();
        btnUserLblComList2 = new javax.swing.JLabel();
        exitIcon = new javax.swing.JLabel();
        dynamicLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(62, 36, 19));
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        background.setBackground(new java.awt.Color(102, 51, 0));
        background.setMaximumSize(new java.awt.Dimension(327, 327));
        background.setPreferredSize(new java.awt.Dimension(300, 804));

        sidebar.setBackground(new java.awt.Color(78, 45, 17));
        sidebar.setForeground(new java.awt.Color(255, 255, 255));

        comLabel.setBackground(new java.awt.Color(78, 45, 17));
        comLabel.setForeground(new java.awt.Color(255, 255, 255));
        comLabel.setPreferredSize(new java.awt.Dimension(336, 80));
        comLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                comLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                comLabelMouseExited(evt);
            }
        });

        company.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        company.setForeground(new java.awt.Color(255, 255, 255));
        company.setText("COMPANY");

        comIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/icons8_organization_50px.png"))); // NOI18N

        javax.swing.GroupLayout comLabelLayout = new javax.swing.GroupLayout(comLabel);
        comLabel.setLayout(comLabelLayout);
        comLabelLayout.setHorizontalGroup(
            comLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(comLabelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(comIcon)
                .addGap(31, 31, 31)
                .addComponent(company, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        comLabelLayout.setVerticalGroup(
            comLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(comLabelLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(comLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comIcon)
                    .addComponent(company, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        branchLabel.setBackground(new java.awt.Color(78, 45, 17));
        branchLabel.setForeground(new java.awt.Color(255, 255, 255));
        branchLabel.setPreferredSize(new java.awt.Dimension(330, 80));
        branchLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                branchLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                branchLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                branchLabelMouseExited(evt);
            }
        });

        branch.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        branch.setForeground(new java.awt.Color(255, 255, 255));
        branch.setText("BRANCH");

        branchIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/icons8_company_50px_1.png"))); // NOI18N

        javax.swing.GroupLayout branchLabelLayout = new javax.swing.GroupLayout(branchLabel);
        branchLabel.setLayout(branchLabelLayout);
        branchLabelLayout.setHorizontalGroup(
            branchLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(branchLabelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(branchIcon)
                .addGap(31, 31, 31)
                .addComponent(branch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );
        branchLabelLayout.setVerticalGroup(
            branchLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, branchLabelLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(branchLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(branchIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(branch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        proLabel.setBackground(new java.awt.Color(78, 45, 17));
        proLabel.setForeground(new java.awt.Color(255, 255, 255));
        proLabel.setPreferredSize(new java.awt.Dimension(330, 80));
        proLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                proLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                proLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                proLabelMouseExited(evt);
            }
        });

        pro.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        pro.setForeground(new java.awt.Color(255, 255, 255));
        pro.setText("PRODUCT");

        proIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/icons8_coffee_50px.png"))); // NOI18N

        javax.swing.GroupLayout proLabelLayout = new javax.swing.GroupLayout(proLabel);
        proLabel.setLayout(proLabelLayout);
        proLabelLayout.setHorizontalGroup(
            proLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proLabelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(proIcon)
                .addGap(31, 31, 31)
                .addComponent(pro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );
        proLabelLayout.setVerticalGroup(
            proLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, proLabelLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(proLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(proIcon)
                    .addComponent(pro, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        staffLabel.setBackground(new java.awt.Color(78, 45, 17));
        staffLabel.setForeground(new java.awt.Color(255, 255, 255));
        staffLabel.setPreferredSize(new java.awt.Dimension(330, 80));
        staffLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                staffLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                staffLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                staffLabelMouseExited(evt);
            }
        });

        staff.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        staff.setForeground(new java.awt.Color(255, 255, 255));
        staff.setText("STAFF");

        staffIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/icons8_user_filled_50px.png"))); // NOI18N

        javax.swing.GroupLayout staffLabelLayout = new javax.swing.GroupLayout(staffLabel);
        staffLabel.setLayout(staffLabelLayout);
        staffLabelLayout.setHorizontalGroup(
            staffLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(staffLabelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(staffIcon)
                .addGap(31, 31, 31)
                .addComponent(staff, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );
        staffLabelLayout.setVerticalGroup(
            staffLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, staffLabelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(staffLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(staffIcon)
                    .addComponent(staff, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        stockLabel.setBackground(new java.awt.Color(78, 45, 17));
        stockLabel.setForeground(new java.awt.Color(255, 255, 255));
        stockLabel.setPreferredSize(new java.awt.Dimension(330, 80));
        stockLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stockLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                stockLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                stockLabelMouseExited(evt);
            }
        });

        stockIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/icons8_box_50px.png"))); // NOI18N

        stock.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        stock.setForeground(new java.awt.Color(255, 255, 255));
        stock.setText("STOCK");

        javax.swing.GroupLayout stockLabelLayout = new javax.swing.GroupLayout(stockLabel);
        stockLabel.setLayout(stockLabelLayout);
        stockLabelLayout.setHorizontalGroup(
            stockLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stockLabelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(stockIcon)
                .addGap(31, 31, 31)
                .addComponent(stock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );
        stockLabelLayout.setVerticalGroup(
            stockLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stockLabelLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(stockLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(stock, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stockIcon))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        comLayer.setBackground(new java.awt.Color(255, 255, 255));
        comLayer.setPreferredSize(new java.awt.Dimension(17, 80));
        comLayer.setLayout(new java.awt.CardLayout());

        comBlink.setBackground(new java.awt.Color(78, 45, 17));
        comBlink.setPreferredSize(new java.awt.Dimension(17, 80));

        javax.swing.GroupLayout comBlinkLayout = new javax.swing.GroupLayout(comBlink);
        comBlink.setLayout(comBlinkLayout);
        comBlinkLayout.setHorizontalGroup(
            comBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        comBlinkLayout.setVerticalGroup(
            comBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        comLayer.add(comBlink, "card2");

        comActive.setBackground(new java.awt.Color(78, 45, 17));

        javax.swing.GroupLayout comActiveLayout = new javax.swing.GroupLayout(comActive);
        comActive.setLayout(comActiveLayout);
        comActiveLayout.setHorizontalGroup(
            comActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        comActiveLayout.setVerticalGroup(
            comActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        comLayer.add(comActive, "card3");

        braLayer.setBackground(new java.awt.Color(255, 255, 255));
        braLayer.setPreferredSize(new java.awt.Dimension(17, 80));
        braLayer.setLayout(new java.awt.CardLayout());

        braBlink.setBackground(new java.awt.Color(78, 45, 17));
        braBlink.setPreferredSize(new java.awt.Dimension(17, 80));

        javax.swing.GroupLayout braBlinkLayout = new javax.swing.GroupLayout(braBlink);
        braBlink.setLayout(braBlinkLayout);
        braBlinkLayout.setHorizontalGroup(
            braBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        braBlinkLayout.setVerticalGroup(
            braBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        braLayer.add(braBlink, "card3");

        braActive.setBackground(new java.awt.Color(78, 45, 17));
        braActive.setPreferredSize(new java.awt.Dimension(17, 80));

        javax.swing.GroupLayout braActiveLayout = new javax.swing.GroupLayout(braActive);
        braActive.setLayout(braActiveLayout);
        braActiveLayout.setHorizontalGroup(
            braActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        braActiveLayout.setVerticalGroup(
            braActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        braLayer.add(braActive, "card2");

        proLayer.setPreferredSize(new java.awt.Dimension(17, 80));
        proLayer.setLayout(new java.awt.CardLayout());

        proActive.setBackground(new java.awt.Color(78, 45, 17));

        javax.swing.GroupLayout proActiveLayout = new javax.swing.GroupLayout(proActive);
        proActive.setLayout(proActiveLayout);
        proActiveLayout.setHorizontalGroup(
            proActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        proActiveLayout.setVerticalGroup(
            proActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        proLayer.add(proActive, "card2");

        proBlink.setBackground(new java.awt.Color(78, 45, 17));

        javax.swing.GroupLayout proBlinkLayout = new javax.swing.GroupLayout(proBlink);
        proBlink.setLayout(proBlinkLayout);
        proBlinkLayout.setHorizontalGroup(
            proBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        proBlinkLayout.setVerticalGroup(
            proBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        proLayer.add(proBlink, "card3");

        staffLayer.setPreferredSize(new java.awt.Dimension(17, 80));
        staffLayer.setLayout(new java.awt.CardLayout());

        staffBlink.setBackground(new java.awt.Color(78, 45, 17));

        javax.swing.GroupLayout staffBlinkLayout = new javax.swing.GroupLayout(staffBlink);
        staffBlink.setLayout(staffBlinkLayout);
        staffBlinkLayout.setHorizontalGroup(
            staffBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        staffBlinkLayout.setVerticalGroup(
            staffBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        staffLayer.add(staffBlink, "card2");

        staffActive.setBackground(new java.awt.Color(78, 45, 17));

        javax.swing.GroupLayout staffActiveLayout = new javax.swing.GroupLayout(staffActive);
        staffActive.setLayout(staffActiveLayout);
        staffActiveLayout.setHorizontalGroup(
            staffActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        staffActiveLayout.setVerticalGroup(
            staffActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        staffLayer.add(staffActive, "card3");

        stockLayer.setPreferredSize(new java.awt.Dimension(17, 80));
        stockLayer.setLayout(new java.awt.CardLayout());

        stockBlink.setBackground(new java.awt.Color(78, 45, 17));

        javax.swing.GroupLayout stockBlinkLayout = new javax.swing.GroupLayout(stockBlink);
        stockBlink.setLayout(stockBlinkLayout);
        stockBlinkLayout.setHorizontalGroup(
            stockBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        stockBlinkLayout.setVerticalGroup(
            stockBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        stockLayer.add(stockBlink, "card2");

        stockActive.setBackground(new java.awt.Color(78, 45, 17));

        javax.swing.GroupLayout stockActiveLayout = new javax.swing.GroupLayout(stockActive);
        stockActive.setLayout(stockActiveLayout);
        stockActiveLayout.setHorizontalGroup(
            stockActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        stockActiveLayout.setVerticalGroup(
            stockActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        stockLayer.add(stockActive, "card3");

        company1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        company1.setForeground(new java.awt.Color(255, 255, 255));
        company1.setText("MANAGEMENT SYSTEM");

        company2.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        company2.setForeground(new java.awt.Color(255, 255, 255));
        company2.setText("COFFEE SHOP");

        userPnl.setBackground(new java.awt.Color(78, 45, 17));
        userPnl.setForeground(new java.awt.Color(255, 255, 255));
        userPnl.setPreferredSize(new java.awt.Dimension(336, 80));
        userPnl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userPnlMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                userPnlMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                userPnlMouseExited(evt);
            }
        });

        userLbl.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        userLbl.setForeground(new java.awt.Color(255, 255, 255));
        userLbl.setText("USER");

        userIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/user_50px.png"))); // NOI18N

        javax.swing.GroupLayout userPnlLayout = new javax.swing.GroupLayout(userPnl);
        userPnl.setLayout(userPnlLayout);
        userPnlLayout.setHorizontalGroup(
            userPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userPnlLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(userIcon)
                .addGap(31, 31, 31)
                .addComponent(userLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        userPnlLayout.setVerticalGroup(
            userPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userPnlLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(userPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userIcon)
                    .addComponent(userLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        userLayp.setPreferredSize(new java.awt.Dimension(17, 80));
        userLayp.setLayout(new java.awt.CardLayout());

        userBlink.setBackground(new java.awt.Color(78, 45, 17));
        userBlink.setPreferredSize(new java.awt.Dimension(17, 80));

        javax.swing.GroupLayout userBlinkLayout = new javax.swing.GroupLayout(userBlink);
        userBlink.setLayout(userBlinkLayout);
        userBlinkLayout.setHorizontalGroup(
            userBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        userBlinkLayout.setVerticalGroup(
            userBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        userLayp.add(userBlink, "card2");

        userActive.setBackground(new java.awt.Color(78, 45, 17));
        userActive.setPreferredSize(new java.awt.Dimension(17, 80));

        javax.swing.GroupLayout userActiveLayout = new javax.swing.GroupLayout(userActive);
        userActive.setLayout(userActiveLayout);
        userActiveLayout.setHorizontalGroup(
            userActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        userActiveLayout.setVerticalGroup(
            userActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        userLayp.add(userActive, "card3");

        dashPnl.setBackground(new java.awt.Color(78, 45, 17));
        dashPnl.setForeground(new java.awt.Color(255, 255, 255));
        dashPnl.setPreferredSize(new java.awt.Dimension(336, 80));
        dashPnl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashPnlMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                dashPnlMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                dashPnlMouseExited(evt);
            }
        });

        dashLbl.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        dashLbl.setForeground(new java.awt.Color(255, 255, 255));
        dashLbl.setText("DASHBOARD");

        dashIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/monitor_50px.png"))); // NOI18N

        javax.swing.GroupLayout dashPnlLayout = new javax.swing.GroupLayout(dashPnl);
        dashPnl.setLayout(dashPnlLayout);
        dashPnlLayout.setHorizontalGroup(
            dashPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashPnlLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(dashIcon)
                .addGap(31, 31, 31)
                .addComponent(dashLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                .addContainerGap())
        );
        dashPnlLayout.setVerticalGroup(
            dashPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashPnlLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(dashPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dashIcon)
                    .addComponent(dashLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        dashLayp.setBackground(new java.awt.Color(78, 45, 17));
        dashLayp.setPreferredSize(new java.awt.Dimension(17, 80));
        dashLayp.setLayout(new java.awt.CardLayout());

        dashBlink.setBackground(new java.awt.Color(78, 45, 17));
        dashBlink.setPreferredSize(new java.awt.Dimension(17, 80));

        javax.swing.GroupLayout dashBlinkLayout = new javax.swing.GroupLayout(dashBlink);
        dashBlink.setLayout(dashBlinkLayout);
        dashBlinkLayout.setHorizontalGroup(
            dashBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        dashBlinkLayout.setVerticalGroup(
            dashBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        dashLayp.add(dashBlink, "card2");

        dashActive.setBackground(new java.awt.Color(78, 45, 17));
        dashActive.setPreferredSize(new java.awt.Dimension(17, 80));

        javax.swing.GroupLayout dashActiveLayout = new javax.swing.GroupLayout(dashActive);
        dashActive.setLayout(dashActiveLayout);
        dashActiveLayout.setHorizontalGroup(
            dashActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        dashActiveLayout.setVerticalGroup(
            dashActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        dashLayp.add(dashActive, "card2");

        btnLogout.setBackground(new java.awt.Color(78, 45, 17));
        btnLogout.setPreferredSize(new java.awt.Dimension(0, 80));
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLogoutMouseClicked(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/shutdown_50px.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("LOGOUT");

        javax.swing.GroupLayout btnLogoutLayout = new javax.swing.GroupLayout(btnLogout);
        btnLogout.setLayout(btnLogoutLayout);
        btnLogoutLayout.setHorizontalGroup(
            btnLogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnLogoutLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel1)
                .addGap(31, 31, 31)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        btnLogoutLayout.setVerticalGroup(
            btnLogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnLogoutLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(btnLogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        supplierPnl.setBackground(new java.awt.Color(78, 45, 17));
        supplierPnl.setForeground(new java.awt.Color(255, 255, 255));
        supplierPnl.setPreferredSize(new java.awt.Dimension(330, 80));
        supplierPnl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                supplierPnlMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                supplierPnlMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                supplierPnlMouseExited(evt);
            }
        });

        stockIcon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/supplier_filled_50px.png"))); // NOI18N

        stock1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        stock1.setForeground(new java.awt.Color(255, 255, 255));
        stock1.setText("SUPPLIER");

        javax.swing.GroupLayout supplierPnlLayout = new javax.swing.GroupLayout(supplierPnl);
        supplierPnl.setLayout(supplierPnlLayout);
        supplierPnlLayout.setHorizontalGroup(
            supplierPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(supplierPnlLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(stockIcon1)
                .addGap(31, 31, 31)
                .addComponent(stock1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );
        supplierPnlLayout.setVerticalGroup(
            supplierPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(supplierPnlLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(supplierPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(stock1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stockIcon1))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        supplierLayp.setPreferredSize(new java.awt.Dimension(17, 80));
        supplierLayp.setLayout(new java.awt.CardLayout());

        supplierBlink.setBackground(new java.awt.Color(78, 45, 17));

        javax.swing.GroupLayout supplierBlinkLayout = new javax.swing.GroupLayout(supplierBlink);
        supplierBlink.setLayout(supplierBlinkLayout);
        supplierBlinkLayout.setHorizontalGroup(
            supplierBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        supplierBlinkLayout.setVerticalGroup(
            supplierBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        supplierLayp.add(supplierBlink, "card2");

        supplierActive.setBackground(new java.awt.Color(78, 45, 17));

        javax.swing.GroupLayout supplierActiveLayout = new javax.swing.GroupLayout(supplierActive);
        supplierActive.setLayout(supplierActiveLayout);
        supplierActiveLayout.setHorizontalGroup(
            supplierActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        supplierActiveLayout.setVerticalGroup(
            supplierActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        supplierLayp.add(supplierActive, "card3");

        javax.swing.GroupLayout sidebarLayout = new javax.swing.GroupLayout(sidebar);
        sidebar.setLayout(sidebarLayout);
        sidebarLayout.setHorizontalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarLayout.createSequentialGroup()
                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(staffLayer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proLayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(comLayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(braLayer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(stockLayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userLayp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dashLayp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dashPnl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                    .addComponent(branchLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                    .addComponent(comLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                    .addComponent(proLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                    .addComponent(staffLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                    .addComponent(stockLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                    .addComponent(userPnl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)))
            .addComponent(btnLogout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
            .addGroup(sidebarLayout.createSequentialGroup()
                .addComponent(supplierLayp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(supplierPnl, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE))
            .addGroup(sidebarLayout.createSequentialGroup()
                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sidebarLayout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(company2))
                    .addGroup(sidebarLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(company1)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        sidebarLayout.setVerticalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(company2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(company1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sidebarLayout.createSequentialGroup()
                        .addComponent(dashPnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(sidebarLayout.createSequentialGroup()
                                .addComponent(userPnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(comLayer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE))
                                .addGap(0, 0, 0)
                                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(braLayer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(branchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(staffLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                                    .addComponent(staffLayer, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE))
                                .addGap(0, 0, 0)
                                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(proLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(proLayer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(stockLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(stockLayer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(userLayp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(dashLayp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(supplierPnl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(supplierLayp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        branchLabel.getAccessibleContext().setAccessibleName("");

        dynamicPanel.setLayout(new java.awt.CardLayout());

        comPanel.setBackground(new java.awt.Color(234, 234, 234));
        comPanel.setPreferredSize(new java.awt.Dimension(1217, 884));

        dynamicComPnl.setBackground(new java.awt.Color(255, 255, 255));
        dynamicComPnl.setLayout(new java.awt.CardLayout());

        pnlComList.setBackground(new java.awt.Color(234, 234, 234));

        jScrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));

        tblCom.setAutoCreateRowSorter(true);
        tblCom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        tblCom.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        tblCom.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Name", "Address", "Email", "Phone", "User", "id"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCom.setEditingColumn(0);
        tblCom.setEditingRow(0);
        tblCom.setGridColor(new java.awt.Color(255, 255, 255));
        tblCom.setOpaque(false);
        tblCom.setPreferredSize(new java.awt.Dimension(1167, 744));
        tblCom.setRowHeight(34);
        tblCom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblComMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblCom);

        lblSearch.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblSearch.setText("Search:");

        txtSearchCom.setBackground(new java.awt.Color(234, 234, 234));
        txtSearchCom.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        txtSearchCom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        txtSearchCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchComActionPerformed(evt);
            }
        });
        txtSearchCom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchComKeyPressed(evt);
            }
        });

        lineSearch.setBackground(new java.awt.Color(0, 0, 0));
        lineSearch.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout lineSearchLayout = new javax.swing.GroupLayout(lineSearch);
        lineSearch.setLayout(lineSearchLayout);
        lineSearchLayout.setHorizontalGroup(
            lineSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 826, Short.MAX_VALUE)
        );
        lineSearchLayout.setVerticalGroup(
            lineSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlComListLayout = new javax.swing.GroupLayout(pnlComList);
        pnlComList.setLayout(pnlComListLayout);
        pnlComListLayout.setHorizontalGroup(
            pnlComListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSearchCom, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
            .addComponent(jScrollPane4)
            .addComponent(lineSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
            .addComponent(lblSearch)
        );
        pnlComListLayout.setVerticalGroup(
            pnlComListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlComListLayout.createSequentialGroup()
                .addComponent(lblSearch)
                .addGap(0, 0, 0)
                .addComponent(txtSearchCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lineSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        dynamicComPnl.add(pnlComList, "card2");

        pnlComAction.setBackground(new java.awt.Color(255, 255, 255));

        lblComAction.setFont(new java.awt.Font("Segoe UI", 0, 42)); // NOI18N
        lblComAction.setText("ADD COMPANY");

        lblName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblName.setText("Name:");

        txtComName.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtComName.setBorder(null);
        txtComName.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtComName.setPreferredSize(new java.awt.Dimension(300, 36));
        txtComName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtComNameFocusLost(evt);
            }
        });
        txtComName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtComNameActionPerformed(evt);
            }
        });

        LineName.setBackground(new java.awt.Color(0, 0, 0));
        LineName.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout LineNameLayout = new javax.swing.GroupLayout(LineName);
        LineName.setLayout(LineNameLayout);
        LineNameLayout.setHorizontalGroup(
            LineNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        LineNameLayout.setVerticalGroup(
            LineNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        btnCom.setBackground(new java.awt.Color(144, 202, 249));
        btnCom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnComMouseClicked(evt);
            }
        });

        lblComBtn.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblComBtn.setForeground(new java.awt.Color(255, 255, 255));
        lblComBtn.setText("ADD");

        javax.swing.GroupLayout btnComLayout = new javax.swing.GroupLayout(btnCom);
        btnCom.setLayout(btnComLayout);
        btnComLayout.setHorizontalGroup(
            btnComLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnComLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblComBtn)
                .addGap(25, 25, 25))
        );
        btnComLayout.setVerticalGroup(
            btnComLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnComLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblComBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        LineEmail.setBackground(new java.awt.Color(0, 0, 0));
        LineEmail.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout LineEmailLayout = new javax.swing.GroupLayout(LineEmail);
        LineEmail.setLayout(LineEmailLayout);
        LineEmailLayout.setHorizontalGroup(
            LineEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        LineEmailLayout.setVerticalGroup(
            LineEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        lbEmail.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbEmail.setText("Email:");

        txtComEmail.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtComEmail.setBorder(null);
        txtComEmail.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtComEmail.setPreferredSize(new java.awt.Dimension(300, 36));
        txtComEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtComEmailFocusLost(evt);
            }
        });
        txtComEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtComEmailActionPerformed(evt);
            }
        });

        LinePhone.setBackground(new java.awt.Color(0, 0, 0));
        LinePhone.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout LinePhoneLayout = new javax.swing.GroupLayout(LinePhone);
        LinePhone.setLayout(LinePhoneLayout);
        LinePhoneLayout.setHorizontalGroup(
            LinePhoneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        LinePhoneLayout.setVerticalGroup(
            LinePhoneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        lblPhone.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblPhone.setText("Phone:");

        txtComPhone.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtComPhone.setBorder(null);
        txtComPhone.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtComPhone.setPreferredSize(new java.awt.Dimension(300, 36));
        txtComPhone.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtComPhoneFocusLost(evt);
            }
        });
        txtComPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtComPhoneActionPerformed(evt);
            }
        });
        txtComPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtComPhoneKeyPressed(evt);
            }
        });

        jScrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));

        txtComAddres.setColumns(5);
        txtComAddres.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtComAddres.setRows(1);
        txtComAddres.setTabSize(5);
        txtComAddres.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        txtComAddres.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtComAddresFocusLost(evt);
            }
        });
        jScrollPane5.setViewportView(txtComAddres);

        LineTxtAddress.setBackground(new java.awt.Color(0, 0, 0));
        LineTxtAddress.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout LineTxtAddressLayout = new javax.swing.GroupLayout(LineTxtAddress);
        LineTxtAddress.setLayout(LineTxtAddressLayout);
        LineTxtAddressLayout.setHorizontalGroup(
            LineTxtAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        LineTxtAddressLayout.setVerticalGroup(
            LineTxtAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        lblAddresss.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblAddresss.setText("Address:");

        javax.swing.GroupLayout pnlComActionLayout = new javax.swing.GroupLayout(pnlComAction);
        pnlComAction.setLayout(pnlComActionLayout);
        pnlComActionLayout.setHorizontalGroup(
            pnlComActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlComActionLayout.createSequentialGroup()
                .addGroup(pnlComActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlComActionLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(pnlComActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlComActionLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(lblComAction)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(LineTxtAddress, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
                            .addComponent(LineName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
                            .addComponent(txtComName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
                            .addComponent(lblName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(LinePhone, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
                            .addComponent(txtComPhone, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
                            .addComponent(lblPhone, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
                            .addComponent(LineEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
                            .addComponent(txtComEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
                            .addComponent(lbEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
                            .addComponent(jScrollPane5)
                            .addComponent(lblAddresss, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(25, 25, 25))
        );
        pnlComActionLayout.setVerticalGroup(
            pnlComActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlComActionLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblComAction)
                .addGap(18, 18, 18)
                .addComponent(lblName)
                .addGap(0, 0, 0)
                .addComponent(txtComName, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(LineName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblPhone)
                .addGap(0, 0, 0)
                .addComponent(txtComPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(LinePhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbEmail)
                .addGap(0, 0, 0)
                .addComponent(txtComEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(LineEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblAddresss)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(LineTxtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        dynamicComPnl.add(pnlComAction, "card2");

        btnComAdd.setBackground(new java.awt.Color(144, 202, 249));
        btnComAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnComAddMouseClicked(evt);
            }
        });

        btnUserIconComAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/plus_40px.png"))); // NOI18N

        btnUserLblComAdd.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLblComAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLblComAdd.setText("ADD");

        javax.swing.GroupLayout btnComAddLayout = new javax.swing.GroupLayout(btnComAdd);
        btnComAdd.setLayout(btnComAddLayout);
        btnComAddLayout.setHorizontalGroup(
            btnComAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnComAddLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIconComAdd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLblComAdd)
                .addGap(25, 25, 25))
        );
        btnComAddLayout.setVerticalGroup(
            btnComAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnUserLblComAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnUserIconComAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnComEdit.setBackground(new java.awt.Color(19, 132, 150));
        btnComEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnComEditMouseClicked(evt);
            }
        });

        btnUserIconComEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/pencil_40px.png"))); // NOI18N

        btnUserLblComEdit.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLblComEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLblComEdit.setText("EDIT");

        javax.swing.GroupLayout btnComEditLayout = new javax.swing.GroupLayout(btnComEdit);
        btnComEdit.setLayout(btnComEditLayout);
        btnComEditLayout.setHorizontalGroup(
            btnComEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnComEditLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIconComEdit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLblComEdit)
                .addGap(25, 25, 25))
        );
        btnComEditLayout.setVerticalGroup(
            btnComEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnComEditLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnUserIconComEdit)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(btnUserLblComEdit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnComDel.setBackground(new java.awt.Color(200, 35, 51));
        btnComDel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnComDelMouseClicked(evt);
            }
        });

        btnUserIconDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/delete_sign_40px.png"))); // NOI18N

        btnUserLblDel.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLblDel.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLblDel.setText("DELETE");

        javax.swing.GroupLayout btnComDelLayout = new javax.swing.GroupLayout(btnComDel);
        btnComDel.setLayout(btnComDelLayout);
        btnComDelLayout.setHorizontalGroup(
            btnComDelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnComDelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIconDel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLblDel)
                .addGap(25, 25, 25))
        );
        btnComDelLayout.setVerticalGroup(
            btnComDelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnComDelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnUserIconDel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(btnUserLblDel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnComList.setBackground(new java.awt.Color(0, 0, 0));
        btnComList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnComListMouseClicked(evt);
            }
        });

        btnUserIconComList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/list_40px.png"))); // NOI18N

        btnUserLblComList.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLblComList.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLblComList.setText("LIST");

        javax.swing.GroupLayout btnComListLayout = new javax.swing.GroupLayout(btnComList);
        btnComList.setLayout(btnComListLayout);
        btnComListLayout.setHorizontalGroup(
            btnComListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnComListLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIconComList)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLblComList)
                .addGap(25, 25, 25))
        );
        btnComListLayout.setVerticalGroup(
            btnComListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnUserLblComList, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(btnComListLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btnUserIconComList, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout comPanelLayout = new javax.swing.GroupLayout(comPanel);
        comPanel.setLayout(comPanelLayout);
        comPanelLayout.setHorizontalGroup(
            comPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 876, Short.MAX_VALUE)
            .addGroup(comPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(comPanelLayout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addGroup(comPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(dynamicComPnl, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(comPanelLayout.createSequentialGroup()
                            .addComponent(btnComAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnComEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnComDel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnComList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(25, 25, 25)))
        );
        comPanelLayout.setVerticalGroup(
            comPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 876, Short.MAX_VALUE)
            .addGroup(comPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(comPanelLayout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addGroup(comPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnComEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnComAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnComDel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnComList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addComponent(dynamicComPnl)
                    .addGap(25, 25, 25)))
        );

        dynamicPanel.add(comPanel, "card2");

        branchPanel.setBackground(new java.awt.Color(234, 234, 234));

        dynamicBranchPnl.setBackground(new java.awt.Color(255, 255, 255));
        dynamicBranchPnl.setLayout(new java.awt.CardLayout());

        pnlBranchList.setBackground(new java.awt.Color(234, 234, 234));

        jScrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));

        tblBranch.setAutoCreateRowSorter(true);
        tblBranch.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        tblBranch.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        tblBranch.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Name", "Address", "Email", "Phone", "Company", "id"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBranch.setEditingColumn(0);
        tblBranch.setEditingRow(0);
        tblBranch.setGridColor(new java.awt.Color(255, 255, 255));
        tblBranch.setOpaque(false);
        tblBranch.setPreferredSize(new java.awt.Dimension(1167, 744));
        tblBranch.setRowHeight(34);
        tblBranch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBranchMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblBranch);

        lblSearch1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblSearch1.setText("Search:");

        txtSearchBranch.setBackground(new java.awt.Color(234, 234, 234));
        txtSearchBranch.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        txtSearchBranch.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        txtSearchBranch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchBranchActionPerformed(evt);
            }
        });
        txtSearchBranch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchBranchKeyPressed(evt);
            }
        });

        lineSearch1.setBackground(new java.awt.Color(0, 0, 0));
        lineSearch1.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout lineSearch1Layout = new javax.swing.GroupLayout(lineSearch1);
        lineSearch1.setLayout(lineSearch1Layout);
        lineSearch1Layout.setHorizontalGroup(
            lineSearch1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 826, Short.MAX_VALUE)
        );
        lineSearch1Layout.setVerticalGroup(
            lineSearch1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlBranchListLayout = new javax.swing.GroupLayout(pnlBranchList);
        pnlBranchList.setLayout(pnlBranchListLayout);
        pnlBranchListLayout.setHorizontalGroup(
            pnlBranchListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSearchBranch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
            .addComponent(jScrollPane6)
            .addComponent(lineSearch1, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
            .addComponent(lblSearch1)
        );
        pnlBranchListLayout.setVerticalGroup(
            pnlBranchListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBranchListLayout.createSequentialGroup()
                .addComponent(lblSearch1)
                .addGap(0, 0, 0)
                .addComponent(txtSearchBranch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lineSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        dynamicBranchPnl.add(pnlBranchList, "card2");

        pnlBranchAction.setBackground(new java.awt.Color(255, 255, 255));

        lblBranchAction.setFont(new java.awt.Font("Segoe UI", 0, 42)); // NOI18N
        lblBranchAction.setText("ADD BRANCH");

        lblName1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblName1.setText("Name:");

        txtBranchName.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtBranchName.setBorder(null);
        txtBranchName.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtBranchName.setPreferredSize(new java.awt.Dimension(300, 36));
        txtBranchName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBranchNameFocusLost(evt);
            }
        });
        txtBranchName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBranchNameActionPerformed(evt);
            }
        });

        LineName1.setBackground(new java.awt.Color(0, 0, 0));
        LineName1.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout LineName1Layout = new javax.swing.GroupLayout(LineName1);
        LineName1.setLayout(LineName1Layout);
        LineName1Layout.setHorizontalGroup(
            LineName1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        LineName1Layout.setVerticalGroup(
            LineName1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        btnBranch.setBackground(new java.awt.Color(144, 202, 249));
        btnBranch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBranchMouseClicked(evt);
            }
        });

        lblBranchBtn.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblBranchBtn.setForeground(new java.awt.Color(255, 255, 255));
        lblBranchBtn.setText("ADD");

        javax.swing.GroupLayout btnBranchLayout = new javax.swing.GroupLayout(btnBranch);
        btnBranch.setLayout(btnBranchLayout);
        btnBranchLayout.setHorizontalGroup(
            btnBranchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnBranchLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblBranchBtn)
                .addGap(25, 25, 25))
        );
        btnBranchLayout.setVerticalGroup(
            btnBranchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnBranchLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblBranchBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        LineEmail1.setBackground(new java.awt.Color(0, 0, 0));
        LineEmail1.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout LineEmail1Layout = new javax.swing.GroupLayout(LineEmail1);
        LineEmail1.setLayout(LineEmail1Layout);
        LineEmail1Layout.setHorizontalGroup(
            LineEmail1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        LineEmail1Layout.setVerticalGroup(
            LineEmail1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        lbEmail1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbEmail1.setText("Email:");

        txtBranchPhone.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtBranchPhone.setBorder(null);
        txtBranchPhone.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtBranchPhone.setPreferredSize(new java.awt.Dimension(300, 36));
        txtBranchPhone.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBranchPhoneFocusLost(evt);
            }
        });
        txtBranchPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBranchPhoneActionPerformed(evt);
            }
        });
        txtBranchPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBranchPhoneKeyPressed(evt);
            }
        });

        LinePhone1.setBackground(new java.awt.Color(0, 0, 0));
        LinePhone1.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout LinePhone1Layout = new javax.swing.GroupLayout(LinePhone1);
        LinePhone1.setLayout(LinePhone1Layout);
        LinePhone1Layout.setHorizontalGroup(
            LinePhone1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        LinePhone1Layout.setVerticalGroup(
            LinePhone1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        lblPhone1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblPhone1.setText("Phone:");

        txtBranchEmail.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtBranchEmail.setBorder(null);
        txtBranchEmail.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtBranchEmail.setPreferredSize(new java.awt.Dimension(300, 36));
        txtBranchEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBranchEmailFocusLost(evt);
            }
        });
        txtBranchEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBranchEmailActionPerformed(evt);
            }
        });
        txtBranchEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBranchEmailKeyPressed(evt);
            }
        });

        jScrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));

        txtBranchAddr.setColumns(5);
        txtBranchAddr.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtBranchAddr.setRows(1);
        txtBranchAddr.setTabSize(5);
        txtBranchAddr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        txtBranchAddr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBranchAddrFocusLost(evt);
            }
        });
        jScrollPane7.setViewportView(txtBranchAddr);

        LineTxtAddress1.setBackground(new java.awt.Color(0, 0, 0));
        LineTxtAddress1.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout LineTxtAddress1Layout = new javax.swing.GroupLayout(LineTxtAddress1);
        LineTxtAddress1.setLayout(LineTxtAddress1Layout);
        LineTxtAddress1Layout.setHorizontalGroup(
            LineTxtAddress1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        LineTxtAddress1Layout.setVerticalGroup(
            LineTxtAddress1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        lblAddresss1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblAddresss1.setText("Address:");

        cbBranchCom.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        cbBranchCom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        lblName2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblName2.setText("Company:");

        javax.swing.GroupLayout pnlBranchActionLayout = new javax.swing.GroupLayout(pnlBranchAction);
        pnlBranchAction.setLayout(pnlBranchActionLayout);
        pnlBranchActionLayout.setHorizontalGroup(
            pnlBranchActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBranchActionLayout.createSequentialGroup()
                .addGroup(pnlBranchActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlBranchActionLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnBranch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlBranchActionLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(pnlBranchActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LineTxtAddress1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
                            .addGroup(pnlBranchActionLayout.createSequentialGroup()
                                .addGroup(pnlBranchActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblName1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlBranchActionLayout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(lblBranchAction))
                                    .addComponent(LineName1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                                    .addComponent(txtBranchName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlBranchActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblName2, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbBranchCom, 0, 379, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBranchActionLayout.createSequentialGroup()
                                .addGroup(pnlBranchActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LinePhone1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                                    .addComponent(lblPhone1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                                    .addComponent(txtBranchPhone, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlBranchActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtBranchEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                                    .addComponent(LineEmail1, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                                    .addComponent(lbEmail1, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)))
                            .addComponent(lblAddresss1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane7))))
                .addGap(25, 25, 25))
        );
        pnlBranchActionLayout.setVerticalGroup(
            pnlBranchActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBranchActionLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblBranchAction)
                .addGap(18, 18, 18)
                .addGroup(pnlBranchActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName1)
                    .addComponent(lblName2))
                .addGap(0, 0, 0)
                .addGroup(pnlBranchActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBranchName, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbBranchCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(pnlBranchActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBranchActionLayout.createSequentialGroup()
                        .addComponent(LineName1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(pnlBranchActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbEmail1)
                            .addComponent(lblPhone1))
                        .addGap(0, 0, 0)
                        .addGroup(pnlBranchActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBranchPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBranchEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(pnlBranchActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LinePhone1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LineEmail1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(lblAddresss1)
                        .addGap(0, 0, 0)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(LineTxtAddress1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBranch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(205, Short.MAX_VALUE))
        );

        dynamicBranchPnl.add(pnlBranchAction, "card2");

        btnBranchAdd.setBackground(new java.awt.Color(144, 202, 249));
        btnBranchAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBranchAddMouseClicked(evt);
            }
        });

        btnUserIconComAdd1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/plus_40px.png"))); // NOI18N

        btnUserLblComAdd1.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLblComAdd1.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLblComAdd1.setText("ADD");

        javax.swing.GroupLayout btnBranchAddLayout = new javax.swing.GroupLayout(btnBranchAdd);
        btnBranchAdd.setLayout(btnBranchAddLayout);
        btnBranchAddLayout.setHorizontalGroup(
            btnBranchAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnBranchAddLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIconComAdd1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLblComAdd1)
                .addGap(25, 25, 25))
        );
        btnBranchAddLayout.setVerticalGroup(
            btnBranchAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnUserLblComAdd1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnUserIconComAdd1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnBranchEdit.setBackground(new java.awt.Color(19, 132, 150));
        btnBranchEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBranchEditMouseClicked(evt);
            }
        });

        btnUserIconComEdit1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/pencil_40px.png"))); // NOI18N

        btnUserLblComEdit1.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLblComEdit1.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLblComEdit1.setText("EDIT");

        javax.swing.GroupLayout btnBranchEditLayout = new javax.swing.GroupLayout(btnBranchEdit);
        btnBranchEdit.setLayout(btnBranchEditLayout);
        btnBranchEditLayout.setHorizontalGroup(
            btnBranchEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnBranchEditLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIconComEdit1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLblComEdit1)
                .addGap(25, 25, 25))
        );
        btnBranchEditLayout.setVerticalGroup(
            btnBranchEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnBranchEditLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnUserIconComEdit1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(btnUserLblComEdit1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnBranchDel.setBackground(new java.awt.Color(200, 35, 51));
        btnBranchDel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBranchDelMouseClicked(evt);
            }
        });

        btnUserIconDel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/delete_sign_40px.png"))); // NOI18N

        btnUserLblDel1.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLblDel1.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLblDel1.setText("DELETE");

        javax.swing.GroupLayout btnBranchDelLayout = new javax.swing.GroupLayout(btnBranchDel);
        btnBranchDel.setLayout(btnBranchDelLayout);
        btnBranchDelLayout.setHorizontalGroup(
            btnBranchDelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnBranchDelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIconDel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLblDel1)
                .addGap(25, 25, 25))
        );
        btnBranchDelLayout.setVerticalGroup(
            btnBranchDelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnBranchDelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnUserIconDel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(btnUserLblDel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnBranchList.setBackground(new java.awt.Color(0, 0, 0));
        btnBranchList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBranchListMouseClicked(evt);
            }
        });

        btnUserIconComList1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/list_40px.png"))); // NOI18N

        btnUserLblComList1.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLblComList1.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLblComList1.setText("LIST");

        javax.swing.GroupLayout btnBranchListLayout = new javax.swing.GroupLayout(btnBranchList);
        btnBranchList.setLayout(btnBranchListLayout);
        btnBranchListLayout.setHorizontalGroup(
            btnBranchListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnBranchListLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIconComList1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLblComList1)
                .addGap(25, 25, 25))
        );
        btnBranchListLayout.setVerticalGroup(
            btnBranchListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnUserLblComList1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(btnBranchListLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btnUserIconComList1, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout branchPanelLayout = new javax.swing.GroupLayout(branchPanel);
        branchPanel.setLayout(branchPanelLayout);
        branchPanelLayout.setHorizontalGroup(
            branchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 876, Short.MAX_VALUE)
            .addGroup(branchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(branchPanelLayout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addGroup(branchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(dynamicBranchPnl, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(branchPanelLayout.createSequentialGroup()
                            .addComponent(btnBranchAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnBranchEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnBranchDel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBranchList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(25, 25, 25)))
        );
        branchPanelLayout.setVerticalGroup(
            branchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 881, Short.MAX_VALUE)
            .addGroup(branchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(branchPanelLayout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addGroup(branchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnBranchEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBranchAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBranchDel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBranchList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addComponent(dynamicBranchPnl)
                    .addGap(25, 25, 25)))
        );

        dynamicPanel.add(branchPanel, "card3");

        proPanel.setBackground(new java.awt.Color(234, 234, 234));

        dynamicProPane.setBackground(new java.awt.Color(255, 255, 255));
        dynamicProPane.setLayout(new java.awt.CardLayout());

        pnlProList.setBackground(new java.awt.Color(234, 234, 234));

        jScrollPane10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));

        tblPro.setAutoCreateRowSorter(true);
        tblPro.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        tblPro.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        tblPro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Name", "Quantity", "Measure", "Stock Category", "id"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Float.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPro.setGridColor(new java.awt.Color(255, 255, 255));
        tblPro.setOpaque(false);
        tblPro.setRowHeight(34);
        tblPro.setSelectionBackground(new java.awt.Color(0, 0, 0));
        jScrollPane10.setViewportView(tblPro);

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel31.setText("Search:");

        txtSearchPro.setBackground(new java.awt.Color(234, 234, 234));
        txtSearchPro.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        txtSearchPro.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        txtSearchPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchProActionPerformed(evt);
            }
        });
        txtSearchPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchProKeyPressed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(0, 0, 0));
        jPanel5.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1478, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlProListLayout = new javax.swing.GroupLayout(pnlProList);
        pnlProList.setLayout(pnlProListLayout);
        pnlProListLayout.setHorizontalGroup(
            pnlProListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSearchPro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1478, Short.MAX_VALUE)
            .addComponent(jScrollPane10)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 1468, Short.MAX_VALUE)
            .addComponent(jLabel31)
        );
        pnlProListLayout.setVerticalGroup(
            pnlProListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlProListLayout.createSequentialGroup()
                .addComponent(jLabel31)
                .addGap(0, 0, 0)
                .addComponent(txtSearchPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        dynamicProPane.add(pnlProList, "card2");

        pnlPro.setBackground(new java.awt.Color(255, 255, 255));

        lblProAction.setFont(new java.awt.Font("Segoe UI", 0, 42)); // NOI18N
        lblProAction.setText("PRODUCT");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel20.setText("Name:");

        txtProName.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtProName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        txtProName.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtProName.setPreferredSize(new java.awt.Dimension(100, 36));
        txtProName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtProNameFocusLost(evt);
            }
        });
        txtProName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProNameActionPerformed(evt);
            }
        });

        jPanel14.setBackground(new java.awt.Color(0, 0, 0));
        jPanel14.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 695, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel23.setText("Product category:");

        cbProCate.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N

        jPanel23.setBackground(new java.awt.Color(0, 0, 0));
        jPanel23.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 705, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        btnPro.setBackground(new java.awt.Color(144, 202, 249));
        btnPro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProMouseClicked(evt);
            }
        });

        lblProBtn.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblProBtn.setForeground(new java.awt.Color(255, 255, 255));
        lblProBtn.setText("ADD");

        javax.swing.GroupLayout btnProLayout = new javax.swing.GroupLayout(btnPro);
        btnPro.setLayout(btnProLayout);
        btnProLayout.setHorizontalGroup(
            btnProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnProLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblProBtn)
                .addGap(25, 25, 25))
        );
        btnProLayout.setVerticalGroup(
            btnProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblProBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel26.setText("Branch:");

        cbProBranch.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N

        jPanel26.setBackground(new java.awt.Color(0, 0, 0));
        jPanel26.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 705, Short.MAX_VALUE)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        txtProDesc.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtProDesc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        txtProDesc.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtProDesc.setPreferredSize(new java.awt.Dimension(100, 36));
        txtProDesc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtProDescFocusLost(evt);
            }
        });
        txtProDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProDescActionPerformed(evt);
            }
        });

        jPanel34.setBackground(new java.awt.Color(0, 0, 0));
        jPanel34.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 705, Short.MAX_VALUE)
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel38.setText("Description:");

        jLabel44.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel44.setText("Search:");

        txtProSearch.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtProSearch.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        txtProSearch.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtProSearch.setPreferredSize(new java.awt.Dimension(100, 36));
        txtProSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtProSearchFocusLost(evt);
            }
        });
        txtProSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProSearchActionPerformed(evt);
            }
        });
        txtProSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtProSearchKeyPressed(evt);
            }
        });

        jPanel28.setBackground(new java.awt.Color(0, 0, 0));
        jPanel28.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jScrollPane13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));

        tblProduct.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        tblProduct.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        tblProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblProduct.setGridColor(new java.awt.Color(255, 255, 255));
        tblProduct.setRowHeight(32);
        tblProduct.setSelectionBackground(new java.awt.Color(0, 0, 0));
        jScrollPane13.setViewportView(tblProduct);

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        jLabel21.setText("LIST");

        btnProDel.setBackground(new java.awt.Color(200, 35, 51));
        btnProDel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProDelMouseClicked(evt);
            }
        });

        lblProVarBtn3.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblProVarBtn3.setForeground(new java.awt.Color(255, 255, 255));
        lblProVarBtn3.setText("DELETE");

        javax.swing.GroupLayout btnProDelLayout = new javax.swing.GroupLayout(btnProDel);
        btnProDel.setLayout(btnProDelLayout);
        btnProDelLayout.setHorizontalGroup(
            btnProDelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnProDelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblProVarBtn3)
                .addGap(25, 25, 25))
        );
        btnProDelLayout.setVerticalGroup(
            btnProDelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblProVarBtn3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnProEdit.setBackground(new java.awt.Color(19, 132, 150));
        btnProEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProEditMouseClicked(evt);
            }
        });

        lblProVarBtn4.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblProVarBtn4.setForeground(new java.awt.Color(255, 255, 255));
        lblProVarBtn4.setText("EDIT");

        javax.swing.GroupLayout btnProEditLayout = new javax.swing.GroupLayout(btnProEdit);
        btnProEdit.setLayout(btnProEditLayout);
        btnProEditLayout.setHorizontalGroup(
            btnProEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnProEditLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblProVarBtn4)
                .addGap(25, 25, 25))
        );
        btnProEditLayout.setVerticalGroup(
            btnProEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblProVarBtn4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlProLayout = new javax.swing.GroupLayout(pnlPro);
        pnlPro.setLayout(pnlProLayout);
        pnlProLayout.setHorizontalGroup(
            pnlProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlProLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator4)
                    .addGroup(pnlProLayout.createSequentialGroup()
                        .addGroup(pnlProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, 695, Short.MAX_VALUE)
                            .addComponent(lblProAction)
                            .addComponent(txtProName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE)
                            .addComponent(txtProDesc, javax.swing.GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE)
                            .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE)))
                    .addGroup(pnlProLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlProLayout.createSequentialGroup()
                        .addGroup(pnlProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbProBranch, 0, 705, Short.MAX_VALUE)
                            .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbProCate, 0, 705, Short.MAX_VALUE)
                            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE)))
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlProLayout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnProEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnProDel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtProSearch, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel28, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1418, Short.MAX_VALUE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25))
        );
        pnlProLayout.setVerticalGroup(
            pnlProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblProAction)
                .addGap(18, 18, 18)
                .addGroup(pnlProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlProLayout.createSequentialGroup()
                        .addGroup(pnlProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38)
                            .addComponent(jLabel20))
                        .addGroup(pnlProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlProLayout.createSequentialGroup()
                                .addGroup(pnlProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtProName, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtProDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlProLayout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel26)
                        .addGap(0, 0, 0)
                        .addComponent(cbProBranch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel23)
                        .addGroup(pnlProLayout.createSequentialGroup()
                            .addGap(25, 25, 25)
                            .addComponent(cbProCate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(btnPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnProDel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnProEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel21))
                .addGap(18, 18, 18)
                .addGroup(pnlProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlProLayout.createSequentialGroup()
                        .addComponent(txtProSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(pnlProLayout.createSequentialGroup()
                        .addComponent(jLabel44)
                        .addGap(63, 63, 63)))
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );

        dynamicProPane.add(pnlPro, "card2");

        pnlProCate.setBackground(new java.awt.Color(255, 255, 255));

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 42)); // NOI18N
        jLabel32.setText("PRODUCT CATEGORY");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel33.setText("Name:");

        txtProCateName.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtProCateName.setBorder(null);
        txtProCateName.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtProCateName.setPreferredSize(new java.awt.Dimension(100, 36));
        txtProCateName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtProCateNameFocusLost(evt);
            }
        });
        txtProCateName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProCateNameActionPerformed(evt);
            }
        });

        jPanel31.setBackground(new java.awt.Color(0, 0, 0));
        jPanel31.setPreferredSize(new java.awt.Dimension(300, 3));

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        btnDelProCate.setBackground(new java.awt.Color(200, 35, 51));
        btnDelProCate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDelProCateMouseClicked(evt);
            }
        });

        lblAdd5.setBackground(new java.awt.Color(200, 35, 51));
        lblAdd5.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblAdd5.setForeground(new java.awt.Color(255, 255, 255));
        lblAdd5.setText("DELETE");

        javax.swing.GroupLayout btnDelProCateLayout = new javax.swing.GroupLayout(btnDelProCate);
        btnDelProCate.setLayout(btnDelProCateLayout);
        btnDelProCateLayout.setHorizontalGroup(
            btnDelProCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnDelProCateLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblAdd5)
                .addGap(25, 25, 25))
        );
        btnDelProCateLayout.setVerticalGroup(
            btnDelProCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAdd5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
        );

        txtProCateDesc.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtProCateDesc.setBorder(null);
        txtProCateDesc.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtProCateDesc.setPreferredSize(new java.awt.Dimension(100, 36));
        txtProCateDesc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtProCateDescFocusLost(evt);
            }
        });
        txtProCateDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProCateDescActionPerformed(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel34.setText("Description:");

        jPanel32.setBackground(new java.awt.Color(0, 0, 0));
        jPanel32.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jScrollPane11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));

        tblProCate.setAutoCreateRowSorter(true);
        tblProCate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        tblProCate.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        tblProCate.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Name", "Description", "id"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProCate.setGridColor(new java.awt.Color(255, 255, 255));
        tblProCate.setRowHeight(34);
        jScrollPane11.setViewportView(tblProCate);

        jLabel35.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel35.setText("Search:");

        txtSearchProCate.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        txtSearchProCate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        txtSearchProCate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchProCateKeyPressed(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(0, 0, 0));
        jPanel6.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1428, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jSeparator2.setBackground(new java.awt.Color(234, 234, 234));
        jSeparator2.setForeground(new java.awt.Color(234, 234, 234));

        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel36.setText("List");

        btnAddProCate.setBackground(new java.awt.Color(144, 202, 249));
        btnAddProCate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddProCateMouseClicked(evt);
            }
        });

        lblProCateAdd.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblProCateAdd.setForeground(new java.awt.Color(255, 255, 255));
        lblProCateAdd.setText("ADD");

        javax.swing.GroupLayout btnAddProCateLayout = new javax.swing.GroupLayout(btnAddProCate);
        btnAddProCate.setLayout(btnAddProCateLayout);
        btnAddProCateLayout.setHorizontalGroup(
            btnAddProCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAddProCateLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblProCateAdd)
                .addGap(25, 25, 25))
        );
        btnAddProCateLayout.setVerticalGroup(
            btnAddProCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblProCateAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnEditProCate.setBackground(new java.awt.Color(19, 132, 150));
        btnEditProCate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditProCateMouseClicked(evt);
            }
        });

        lblAdd8.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblAdd8.setForeground(new java.awt.Color(255, 255, 255));
        lblAdd8.setText("EDIT");

        javax.swing.GroupLayout btnEditProCateLayout = new javax.swing.GroupLayout(btnEditProCate);
        btnEditProCate.setLayout(btnEditProCateLayout);
        btnEditProCateLayout.setHorizontalGroup(
            btnEditProCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnEditProCateLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblAdd8)
                .addGap(25, 25, 25))
        );
        btnEditProCateLayout.setVerticalGroup(
            btnEditProCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAdd8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlProCateLayout = new javax.swing.GroupLayout(pnlProCate);
        pnlProCate.setLayout(pnlProCateLayout);
        pnlProCateLayout.setHorizontalGroup(
            pnlProCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProCateLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlProCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlProCateLayout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlProCateLayout.createSequentialGroup()
                        .addGroup(pnlProCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlProCateLayout.createSequentialGroup()
                                .addGroup(pnlProCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
                                    .addGroup(pnlProCateLayout.createSequentialGroup()
                                        .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
                                        .addGap(194, 194, 194))
                                    .addComponent(txtProCateName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlProCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE)
                                    .addComponent(txtProCateDesc, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
                                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(btnAddProCate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane11)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 1418, Short.MAX_VALUE)
                            .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator2)
                            .addGroup(pnlProCateLayout.createSequentialGroup()
                                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEditProCate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelProCate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtSearchProCate))
                        .addGap(25, 25, 25))))
        );
        pnlProCateLayout.setVerticalGroup(
            pnlProCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProCateLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel32)
                .addGap(25, 25, 25)
                .addGroup(pnlProCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlProCateLayout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addGap(0, 0, 0)
                        .addComponent(txtProCateName, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlProCateLayout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addGap(0, 0, 0)
                        .addGroup(pnlProCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlProCateLayout.createSequentialGroup()
                                .addComponent(txtProCateDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnAddProCate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlProCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlProCateLayout.createSequentialGroup()
                        .addGroup(pnlProCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnDelProCate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36))
                        .addGap(25, 25, 25)
                        .addComponent(jLabel35)
                        .addGap(0, 0, 0)
                        .addComponent(txtSearchProCate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                        .addGap(25, 25, 25))
                    .addGroup(pnlProCateLayout.createSequentialGroup()
                        .addComponent(btnEditProCate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        dynamicProPane.add(pnlProCate, "card2");

        pnlProVar.setBackground(new java.awt.Color(255, 255, 255));

        lblProVarAction.setFont(new java.awt.Font("Segoe UI", 0, 42)); // NOI18N
        lblProVarAction.setText("PRODUCT VARIANT");

        btnProVar.setBackground(new java.awt.Color(144, 202, 249));
        btnProVar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProVarMouseClicked(evt);
            }
        });

        lblProVarBtn.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblProVarBtn.setForeground(new java.awt.Color(255, 255, 255));
        lblProVarBtn.setText("ADD");

        javax.swing.GroupLayout btnProVarLayout = new javax.swing.GroupLayout(btnProVar);
        btnProVar.setLayout(btnProVarLayout);
        btnProVarLayout.setHorizontalGroup(
            btnProVarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnProVarLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblProVarBtn)
                .addGap(25, 25, 25))
        );
        btnProVarLayout.setVerticalGroup(
            btnProVarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblProVarBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        txtProVarPrice.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtProVarPrice.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        txtProVarPrice.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtProVarPrice.setPreferredSize(new java.awt.Dimension(100, 36));
        txtProVarPrice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtProVarPriceFocusLost(evt);
            }
        });
        txtProVarPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProVarPriceActionPerformed(evt);
            }
        });
        txtProVarPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtProVarPriceKeyPressed(evt);
            }
        });

        jPanel25.setBackground(new java.awt.Color(0, 0, 0));
        jPanel25.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 521, Short.MAX_VALUE)
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        txtProVarSellingPrice.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtProVarSellingPrice.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        txtProVarSellingPrice.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtProVarSellingPrice.setPreferredSize(new java.awt.Dimension(100, 36));
        txtProVarSellingPrice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtProVarSellingPriceFocusLost(evt);
            }
        });
        txtProVarSellingPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProVarSellingPriceActionPerformed(evt);
            }
        });
        txtProVarSellingPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtProVarSellingPriceKeyPressed(evt);
            }
        });

        jPanel35.setBackground(new java.awt.Color(0, 0, 0));
        jPanel35.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 521, Short.MAX_VALUE)
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel25.setText("Selling price:");

        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel39.setText("Price:");

        jLabel40.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel40.setText("Size:");

        cbProVarSize.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        cbProVarSize.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Large", "Medium", "Small" }));

        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel41.setText("Product:");

        cbProVarProduct.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        cbProVarProduct.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ice", "Hot" }));

        jPanel36.setBackground(new java.awt.Color(0, 0, 0));
        jPanel36.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 521, Short.MAX_VALUE)
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jPanel37.setBackground(new java.awt.Color(0, 0, 0));
        jPanel37.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 521, Short.MAX_VALUE)
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        btnProVarImg.setBackground(new java.awt.Color(225, 225, 225));
        btnProVarImg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProVarImgMouseClicked(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        jLabel43.setText("UPLOAD IMAGE");

        javax.swing.GroupLayout btnProVarImgLayout = new javax.swing.GroupLayout(btnProVarImg);
        btnProVarImg.setLayout(btnProVarImgLayout);
        btnProVarImgLayout.setHorizontalGroup(
            btnProVarImgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnProVarImgLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel43)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnProVarImgLayout.setVerticalGroup(
            btnProVarImgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jDesktopPane2.setBackground(new java.awt.Color(255, 255, 255));
        jDesktopPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        jDesktopPane2.setPreferredSize(new java.awt.Dimension(350, 322));

        proImg.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        proImg.setPreferredSize(new java.awt.Dimension(350, 250));

        jDesktopPane2.setLayer(proImg, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane2Layout = new javax.swing.GroupLayout(jDesktopPane2);
        jDesktopPane2.setLayout(jDesktopPane2Layout);
        jDesktopPane2Layout.setHorizontalGroup(
            jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(proImg, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jDesktopPane2Layout.setVerticalGroup(
            jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(proImg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jSeparator3.setBackground(new java.awt.Color(234, 234, 234));
        jSeparator3.setForeground(new java.awt.Color(234, 234, 234));

        jScrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));

        tblProVar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        tblProVar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        tblProVar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblProVar.setGridColor(new java.awt.Color(255, 255, 255));
        tblProVar.setRowHeight(32);
        tblProVar.setSelectionBackground(new java.awt.Color(0, 0, 0));
        jScrollPane12.setViewportView(tblProVar);

        btnProVarDel.setBackground(new java.awt.Color(200, 35, 51));
        btnProVarDel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProVarDelMouseClicked(evt);
            }
        });

        lblProVarBtn1.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblProVarBtn1.setForeground(new java.awt.Color(255, 255, 255));
        lblProVarBtn1.setText("DELETE");

        javax.swing.GroupLayout btnProVarDelLayout = new javax.swing.GroupLayout(btnProVarDel);
        btnProVarDel.setLayout(btnProVarDelLayout);
        btnProVarDelLayout.setHorizontalGroup(
            btnProVarDelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnProVarDelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblProVarBtn1)
                .addGap(25, 25, 25))
        );
        btnProVarDelLayout.setVerticalGroup(
            btnProVarDelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblProVarBtn1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnProVarEdit.setBackground(new java.awt.Color(19, 132, 150));
        btnProVarEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProVarEditMouseClicked(evt);
            }
        });

        lblProVarBtn2.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblProVarBtn2.setForeground(new java.awt.Color(255, 255, 255));
        lblProVarBtn2.setText("EDIT");

        javax.swing.GroupLayout btnProVarEditLayout = new javax.swing.GroupLayout(btnProVarEdit);
        btnProVarEdit.setLayout(btnProVarEditLayout);
        btnProVarEditLayout.setHorizontalGroup(
            btnProVarEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnProVarEditLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblProVarBtn2)
                .addGap(25, 25, 25))
        );
        btnProVarEditLayout.setVerticalGroup(
            btnProVarEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblProVarBtn2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel42.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel42.setText("Search:");

        txtProVarSearch.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtProVarSearch.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        txtProVarSearch.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtProVarSearch.setPreferredSize(new java.awt.Dimension(100, 36));
        txtProVarSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtProVarSearchFocusLost(evt);
            }
        });
        txtProVarSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProVarSearchActionPerformed(evt);
            }
        });
        txtProVarSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtProVarSearchKeyPressed(evt);
            }
        });

        jPanel27.setBackground(new java.awt.Color(0, 0, 0));
        jPanel27.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        jLabel4.setText("LIST");

        btnProductStock.setBackground(new java.awt.Color(113, 113, 113));
        btnProductStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductStockMouseClicked(evt);
            }
        });

        lblProVarBtn5.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblProVarBtn5.setForeground(new java.awt.Color(255, 255, 255));
        lblProVarBtn5.setText("STOCK");

        javax.swing.GroupLayout btnProductStockLayout = new javax.swing.GroupLayout(btnProductStock);
        btnProductStock.setLayout(btnProductStockLayout);
        btnProductStockLayout.setHorizontalGroup(
            btnProductStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnProductStockLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblProVarBtn5)
                .addGap(25, 25, 25))
        );
        btnProductStockLayout.setVerticalGroup(
            btnProductStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblProVarBtn5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlProVarLayout = new javax.swing.GroupLayout(pnlProVar);
        pnlProVar.setLayout(pnlProVarLayout);
        pnlProVarLayout.setHorizontalGroup(
            pnlProVarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlProVarLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlProVarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlProVarLayout.createSequentialGroup()
                        .addGroup(pnlProVarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlProVarLayout.createSequentialGroup()
                                .addGroup(pnlProVarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
                                    .addComponent(txtProVarPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
                                    .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlProVarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtProVarSellingPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
                                    .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)))
                            .addGroup(pnlProVarLayout.createSequentialGroup()
                                .addGroup(pnlProVarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbProVarSize, 0, 521, Short.MAX_VALUE)
                                    .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
                                    .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlProVarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel41, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
                                    .addComponent(cbProVarProduct, javax.swing.GroupLayout.Alignment.TRAILING, 0, 521, Short.MAX_VALUE)
                                    .addComponent(jPanel37, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
                                    .addComponent(btnProVar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jSeparator3)
                            .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 1050, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlProVarLayout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnProductStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnProVarEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnProVarDel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtProVarSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, 1050, Short.MAX_VALUE)
                            .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlProVarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jDesktopPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnProVarImg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(lblProVarAction))
                .addGap(25, 25, 25))
        );
        pnlProVarLayout.setVerticalGroup(
            pnlProVarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProVarLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblProVarAction)
                .addGap(18, 18, 18)
                .addGroup(pnlProVarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnProVarImg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlProVarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlProVarLayout.createSequentialGroup()
                            .addComponent(txtProVarPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnlProVarLayout.createSequentialGroup()
                            .addGroup(pnlProVarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel25)
                                .addComponent(jLabel39))
                            .addGap(0, 0, 0)
                            .addComponent(txtProVarSellingPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(pnlProVarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlProVarLayout.createSequentialGroup()
                        .addGroup(pnlProVarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlProVarLayout.createSequentialGroup()
                                .addComponent(jLabel41)
                                .addGap(0, 0, 0)
                                .addComponent(cbProVarProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlProVarLayout.createSequentialGroup()
                                .addComponent(jLabel40)
                                .addGap(0, 0, 0)
                                .addComponent(cbProVarSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnProVar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlProVarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnProVarDel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnProVarEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnProductStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlProVarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlProVarLayout.createSequentialGroup()
                                .addComponent(txtProVarSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                            .addGroup(pnlProVarLayout.createSequentialGroup()
                                .addComponent(jLabel42)
                                .addGap(63, 63, 63)))
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))
                    .addComponent(jDesktopPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE))
                .addGap(25, 25, 25))
        );

        dynamicProPane.add(pnlProVar, "card2");

        btnProductVar.setBackground(new java.awt.Color(0, 0, 0));
        btnProductVar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductVarMouseClicked(evt);
            }
        });

        btnUserIcon8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/product_40px.png"))); // NOI18N

        btnUserLbl8.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLbl8.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLbl8.setText("PRODUCT VARIANT");

        javax.swing.GroupLayout btnProductVarLayout = new javax.swing.GroupLayout(btnProductVar);
        btnProductVar.setLayout(btnProductVarLayout);
        btnProductVarLayout.setHorizontalGroup(
            btnProductVarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnProductVarLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIcon8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLbl8)
                .addGap(25, 25, 25))
        );
        btnProductVarLayout.setVerticalGroup(
            btnProductVarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnProductVarLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnUserIcon8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(btnUserLbl8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnProduct.setBackground(new java.awt.Color(0, 0, 0));
        btnProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductMouseClicked(evt);
            }
        });

        btnUserIcon5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/product_40px.png"))); // NOI18N

        btnUserLbl5.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLbl5.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLbl5.setText("PRODUCT");

        javax.swing.GroupLayout btnProductLayout = new javax.swing.GroupLayout(btnProduct);
        btnProduct.setLayout(btnProductLayout);
        btnProductLayout.setHorizontalGroup(
            btnProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnProductLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIcon5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLbl5)
                .addGap(25, 25, 25))
        );
        btnProductLayout.setVerticalGroup(
            btnProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnUserLbl5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnUserIcon5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnProductCate.setBackground(new java.awt.Color(0, 0, 0));
        btnProductCate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductCateMouseClicked(evt);
            }
        });

        btnUserIcon12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/category_40px.png"))); // NOI18N

        btnUserLbl12.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLbl12.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLbl12.setText("PRODUCT CATEGORIES");

        javax.swing.GroupLayout btnProductCateLayout = new javax.swing.GroupLayout(btnProductCate);
        btnProductCate.setLayout(btnProductCateLayout);
        btnProductCateLayout.setHorizontalGroup(
            btnProductCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnProductCateLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIcon12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLbl12)
                .addGap(25, 25, 25))
        );
        btnProductCateLayout.setVerticalGroup(
            btnProductCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnProductCateLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnUserIcon12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(btnUserLbl12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnProList.setBackground(new java.awt.Color(0, 0, 0));
        btnProList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProListMouseClicked(evt);
            }
        });

        btnUserIcon13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/list_40px.png"))); // NOI18N

        btnUserLbl13.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLbl13.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLbl13.setText("LIST");

        javax.swing.GroupLayout btnProListLayout = new javax.swing.GroupLayout(btnProList);
        btnProList.setLayout(btnProListLayout);
        btnProListLayout.setHorizontalGroup(
            btnProListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnProListLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIcon13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLbl13)
                .addGap(25, 25, 25))
        );
        btnProListLayout.setVerticalGroup(
            btnProListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnUserLbl13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(btnProListLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btnUserIcon13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnStockPro.setBackground(new java.awt.Color(113, 113, 113));
        btnStockPro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockProMouseClicked(evt);
            }
        });

        btnRoleIcon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/product_40px.png"))); // NOI18N

        btnRoleLbl3.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnRoleLbl3.setForeground(new java.awt.Color(255, 255, 255));
        btnRoleLbl3.setText("STOCK");

        javax.swing.GroupLayout btnStockProLayout = new javax.swing.GroupLayout(btnStockPro);
        btnStockPro.setLayout(btnStockProLayout);
        btnStockProLayout.setHorizontalGroup(
            btnStockProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnStockProLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnRoleIcon4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRoleLbl3)
                .addGap(25, 25, 25))
        );
        btnStockProLayout.setVerticalGroup(
            btnStockProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnRoleLbl3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnStockProLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRoleIcon4)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout proPanelLayout = new javax.swing.GroupLayout(proPanel);
        proPanel.setLayout(proPanelLayout);
        proPanelLayout.setHorizontalGroup(
            proPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(proPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dynamicProPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(proPanelLayout.createSequentialGroup()
                        .addComponent(btnProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnProductVar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnProductCate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnStockPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnProList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(25, 25, 25))
        );
        proPanelLayout.setVerticalGroup(
            proPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, proPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(proPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnProductVar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnProductCate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnProList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnStockPro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(dynamicProPane)
                .addGap(25, 25, 25))
        );

        dynamicPanel.add(proPanel, "card4");

        javax.swing.GroupLayout staffPanelLayout = new javax.swing.GroupLayout(staffPanel);
        staffPanel.setLayout(staffPanelLayout);
        staffPanelLayout.setHorizontalGroup(
            staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        staffPanelLayout.setVerticalGroup(
            staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 881, Short.MAX_VALUE)
        );

        dynamicPanel.add(staffPanel, "card5");

        userPanel.setBackground(new java.awt.Color(234, 234, 234));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        jScrollPane1.setOpaque(false);

        userTbl.setAutoCreateRowSorter(true);
        userTbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        userTbl.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        userTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "#", "Name", "Email", "Gender", "Status", "id"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        userTbl.setGridColor(new java.awt.Color(255, 255, 255));
        userTbl.setOpaque(false);
        userTbl.setRowHeight(34);
        userTbl.setSelectionBackground(new java.awt.Color(0, 0, 0));
        userTbl.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(userTbl);
        userTbl.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        btnAddUser.setBackground(new java.awt.Color(144, 202, 249));
        btnAddUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddUserMouseClicked(evt);
            }
        });

        btnUserIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/add_user_40px.png"))); // NOI18N

        btnUserLbl.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLbl.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLbl.setText("ADD");

        javax.swing.GroupLayout btnAddUserLayout = new javax.swing.GroupLayout(btnAddUser);
        btnAddUser.setLayout(btnAddUserLayout);
        btnAddUserLayout.setHorizontalGroup(
            btnAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAddUserLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIcon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLbl)
                .addGap(25, 25, 25))
        );
        btnAddUserLayout.setVerticalGroup(
            btnAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAddUserLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnUserIcon)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(btnUserLbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnRole.setBackground(new java.awt.Color(113, 113, 113));
        btnRole.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRoleMouseClicked(evt);
            }
        });

        btnRoleIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/admin_settings_male_40px.png"))); // NOI18N

        btnRoleLbl.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnRoleLbl.setForeground(new java.awt.Color(255, 255, 255));
        btnRoleLbl.setText("ROLE");

        javax.swing.GroupLayout btnRoleLayout = new javax.swing.GroupLayout(btnRole);
        btnRole.setLayout(btnRoleLayout);
        btnRoleLayout.setHorizontalGroup(
            btnRoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnRoleLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnRoleIcon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRoleLbl)
                .addGap(25, 25, 25))
        );
        btnRoleLayout.setVerticalGroup(
            btnRoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnRoleLbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnRoleLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRoleIcon)
                .addGap(15, 15, 15))
        );

        btnPermission.setBackground(new java.awt.Color(113, 113, 113));
        btnPermission.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPermissionMouseClicked(evt);
            }
        });

        btnRoleIcon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/restriction_shield_40px.png"))); // NOI18N

        btnRoleLbl1.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnRoleLbl1.setForeground(new java.awt.Color(255, 255, 255));
        btnRoleLbl1.setText("PERMISSION");

        javax.swing.GroupLayout btnPermissionLayout = new javax.swing.GroupLayout(btnPermission);
        btnPermission.setLayout(btnPermissionLayout);
        btnPermissionLayout.setHorizontalGroup(
            btnPermissionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPermissionLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnRoleIcon1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRoleLbl1)
                .addGap(25, 25, 25))
        );
        btnPermissionLayout.setVerticalGroup(
            btnPermissionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnRoleLbl1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnPermissionLayout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(btnRoleIcon1)
                .addGap(15, 15, 15))
        );

        btnEditUser.setBackground(new java.awt.Color(19, 132, 150));
        btnEditUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditUserMouseClicked(evt);
            }
        });

        btnUserIcon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/pencil_40px.png"))); // NOI18N

        btnUserLbl1.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLbl1.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLbl1.setText("EDIT");

        javax.swing.GroupLayout btnEditUserLayout = new javax.swing.GroupLayout(btnEditUser);
        btnEditUser.setLayout(btnEditUserLayout);
        btnEditUserLayout.setHorizontalGroup(
            btnEditUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnEditUserLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIcon1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLbl1)
                .addGap(25, 25, 25))
        );
        btnEditUserLayout.setVerticalGroup(
            btnEditUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnUserLbl1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnEditUserLayout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(btnUserIcon1)
                .addGap(15, 15, 15))
        );

        btnChangePass.setBackground(new java.awt.Color(35, 39, 43));
        btnChangePass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnChangePassMouseClicked(evt);
            }
        });

        btnUserIcon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/pencil_40px.png"))); // NOI18N

        btnUserLbl2.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLbl2.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLbl2.setText("PASSWORD");

        javax.swing.GroupLayout btnChangePassLayout = new javax.swing.GroupLayout(btnChangePass);
        btnChangePass.setLayout(btnChangePassLayout);
        btnChangePassLayout.setHorizontalGroup(
            btnChangePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnChangePassLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIcon2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLbl2)
                .addGap(25, 25, 25))
        );
        btnChangePassLayout.setVerticalGroup(
            btnChangePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnUserLbl2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(btnChangePassLayout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(btnUserIcon2)
                .addGap(15, 15, 15))
        );

        btnDelUser.setBackground(new java.awt.Color(200, 35, 51));
        btnDelUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDelUserMouseClicked(evt);
            }
        });

        btnUserIcon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/delete_sign_40px.png"))); // NOI18N

        btnUserLbl3.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLbl3.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLbl3.setText("DELETE");

        javax.swing.GroupLayout btnDelUserLayout = new javax.swing.GroupLayout(btnDelUser);
        btnDelUser.setLayout(btnDelUserLayout);
        btnDelUserLayout.setHorizontalGroup(
            btnDelUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnDelUserLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIcon3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLbl3)
                .addGap(25, 25, 25))
        );
        btnDelUserLayout.setVerticalGroup(
            btnDelUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnDelUserLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(btnUserIcon3)
                .addGap(15, 15, 15))
            .addComponent(btnUserLbl3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnUserRefresh.setBackground(new java.awt.Color(35, 39, 43));
        btnUserRefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUserRefreshMouseClicked(evt);
            }
        });

        btnRoleIcon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/refresh_40px.png"))); // NOI18N

        javax.swing.GroupLayout btnUserRefreshLayout = new javax.swing.GroupLayout(btnUserRefresh);
        btnUserRefresh.setLayout(btnUserRefreshLayout);
        btnUserRefreshLayout.setHorizontalGroup(
            btnUserRefreshLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnUserRefreshLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRoleIcon2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnUserRefreshLayout.setVerticalGroup(
            btnUserRefreshLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnUserRefreshLayout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(btnRoleIcon2)
                .addGap(15, 15, 15))
        );

        txtSearchUser.setBackground(new java.awt.Color(234, 234, 234));
        txtSearchUser.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        txtSearchUser.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        txtSearchUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchUserActionPerformed(evt);
            }
        });
        txtSearchUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchUserKeyPressed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Search:");

        javax.swing.GroupLayout userPanelLayout = new javax.swing.GroupLayout(userPanel);
        userPanel.setLayout(userPanelLayout);
        userPanelLayout.setHorizontalGroup(
            userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, userPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1409, Short.MAX_VALUE)
                    .addComponent(txtSearchUser)
                    .addGroup(userPanelLayout.createSequentialGroup()
                        .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(userPanelLayout.createSequentialGroup()
                                .addComponent(btnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnChangePass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(btnDelUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnPermission, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUserRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(25, 25, 25))
        );
        userPanelLayout.setVerticalGroup(
            userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(userPanelLayout.createSequentialGroup()
                        .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnEditUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnChangePass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnRole, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnPermission, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(21, 21, 21)
                        .addComponent(jLabel3))
                    .addComponent(btnUserRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(txtSearchUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );

        dynamicPanel.add(userPanel, "card6");

        javax.swing.GroupLayout dashPanelLayout = new javax.swing.GroupLayout(dashPanel);
        dashPanel.setLayout(dashPanelLayout);
        dashPanelLayout.setHorizontalGroup(
            dashPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 876, Short.MAX_VALUE)
        );
        dashPanelLayout.setVerticalGroup(
            dashPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 881, Short.MAX_VALUE)
        );

        dynamicPanel.add(dashPanel, "card6");

        stockPanel.setBackground(new java.awt.Color(234, 234, 234));
        stockPanel.setToolTipText("");

        dynamicStockPane.setBackground(new java.awt.Color(255, 255, 255));
        dynamicStockPane.setLayout(new java.awt.CardLayout());

        pnlStockImport.setBackground(new java.awt.Color(255, 255, 255));

        lblStockAction.setFont(new java.awt.Font("Segoe UI", 0, 42)); // NOI18N
        lblStockAction.setText("IMPORT STOCK");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("Name:");

        txtStockName.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtStockName.setBorder(null);
        txtStockName.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtStockName.setPreferredSize(new java.awt.Dimension(100, 36));
        txtStockName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtStockNameFocusLost(evt);
            }
        });
        txtStockName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStockNameActionPerformed(evt);
            }
        });

        jPanel9.setBackground(new java.awt.Color(0, 0, 0));
        jPanel9.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        dpStockExpired.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        dpStockExpired.setDateFormatString("yyyy-MM-dd");
        dpStockExpired.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N

        jPanel10.setBackground(new java.awt.Color(0, 0, 0));
        jPanel10.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setText("Expired date:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setText("Quantity");

        txtStockQty.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtStockQty.setBorder(null);
        txtStockQty.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtStockQty.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtStockQtyFocusLost(evt);
            }
        });
        txtStockQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStockQtyActionPerformed(evt);
            }
        });
        txtStockQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtStockQtyKeyPressed(evt);
            }
        });

        jPanel11.setBackground(new java.awt.Color(0, 0, 0));
        jPanel11.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setText("Stock category:");

        cbStockCate.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N

        jPanel12.setBackground(new java.awt.Color(0, 0, 0));
        jPanel12.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel13.setText("Measure:");

        cbStockMeasure.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        cbStockMeasure.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "g", "kg" }));

        jPanel13.setBackground(new java.awt.Color(0, 0, 0));
        jPanel13.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setText("Alert quantity:");

        txtStockAlertQty.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtStockAlertQty.setBorder(null);
        txtStockAlertQty.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtStockAlertQty.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtStockAlertQtyFocusLost(evt);
            }
        });
        txtStockAlertQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStockAlertQtyActionPerformed(evt);
            }
        });
        txtStockAlertQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtStockAlertQtyKeyPressed(evt);
            }
        });

        jPanel15.setBackground(new java.awt.Color(0, 0, 0));
        jPanel15.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setText("Price:");

        txtStockPrice.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtStockPrice.setBorder(null);
        txtStockPrice.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtStockPrice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtStockPriceFocusLost(evt);
            }
        });
        txtStockPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStockPriceActionPerformed(evt);
            }
        });
        txtStockPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtStockPriceKeyPressed(evt);
            }
        });

        jPanel16.setBackground(new java.awt.Color(0, 0, 0));
        jPanel16.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        cbStockCompany.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel16.setText("Company:");

        jPanel17.setBackground(new java.awt.Color(0, 0, 0));
        jPanel17.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        cbStockBranch.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel17.setText("Branch:");

        jPanel18.setBackground(new java.awt.Color(0, 0, 0));
        jPanel18.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        cbStockSupplier.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel18.setText("Supplier:");

        jPanel19.setBackground(new java.awt.Color(0, 0, 0));
        jPanel19.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        btnStock.setBackground(new java.awt.Color(144, 202, 249));
        btnStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockMouseClicked(evt);
            }
        });

        lblStockBtn.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblStockBtn.setForeground(new java.awt.Color(255, 255, 255));
        lblStockBtn.setText("Add");

        javax.swing.GroupLayout btnStockLayout = new javax.swing.GroupLayout(btnStock);
        btnStock.setLayout(btnStockLayout);
        btnStockLayout.setHorizontalGroup(
            btnStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnStockLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblStockBtn)
                .addGap(25, 25, 25))
        );
        btnStockLayout.setVerticalGroup(
            btnStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnStockLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblStockBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlStockImportLayout = new javax.swing.GroupLayout(pnlStockImport);
        pnlStockImport.setLayout(pnlStockImportLayout);
        pnlStockImportLayout.setHorizontalGroup(
            pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStockImportLayout.createSequentialGroup()
                .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlStockImportLayout.createSequentialGroup()
                        .addContainerGap(1063, Short.MAX_VALUE)
                        .addComponent(btnStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlStockImportLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblStockAction)
                            .addGroup(pnlStockImportLayout.createSequentialGroup()
                                .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlStockImportLayout.createSequentialGroup()
                                        .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                                            .addComponent(txtStockName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(pnlStockImportLayout.createSequentialGroup()
                                                .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(txtStockQty)
                                                    .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(pnlStockImportLayout.createSequentialGroup()
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStockImportLayout.createSequentialGroup()
                                                        .addGap(18, 18, 18)
                                                        .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(cbStockMeasure, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                                            .addComponent(cbStockCompany, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                                            .addGroup(pnlStockImportLayout.createSequentialGroup()
                                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                        .addGap(18, 18, 18))
                                    .addGroup(pnlStockImportLayout.createSequentialGroup()
                                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(194, 194, 194)))
                                .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlStockImportLayout.createSequentialGroup()
                                        .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(dpStockExpired, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                                            .addComponent(txtStockAlertQty, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cbStockBranch, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                                            .addGroup(pnlStockImportLayout.createSequentialGroup()
                                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 362, Short.MAX_VALUE)))
                                        .addGap(18, 18, 18))
                                    .addGroup(pnlStockImportLayout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(207, 207, 207)))
                                .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbStockCate, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                                    .addComponent(txtStockPrice, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbStockSupplier, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                                    .addGroup(pnlStockImportLayout.createSequentialGroup()
                                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 191, Short.MAX_VALUE))
                                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addGap(25, 25, 25))
        );
        pnlStockImportLayout.setVerticalGroup(
            pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStockImportLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlStockImportLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(0, 0, 0)
                        .addComponent(cbStockBranch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlStockImportLayout.createSequentialGroup()
                        .addComponent(lblStockAction)
                        .addGap(25, 25, 25)
                        .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(jLabel10))
                            .addComponent(jLabel12))
                        .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtStockName, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dpStockExpired, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbStockCate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlStockImportLayout.createSequentialGroup()
                                .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel13))
                                .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtStockQty, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbStockMeasure, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlStockImportLayout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(0, 0, 0)
                                .addComponent(txtStockAlertQty, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlStockImportLayout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(0, 0, 0)
                                .addComponent(txtStockPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel16)
                        .addGap(0, 0, 0)
                        .addComponent(cbStockCompany, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlStockImportLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(0, 0, 0)
                        .addComponent(cbStockSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(btnStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        dynamicStockPane.add(pnlStockImport, "card2");

        pnlStockList.setBackground(new java.awt.Color(234, 234, 234));

        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));

        tblStock.setAutoCreateRowSorter(true);
        tblStock.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        tblStock.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        tblStock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Name", "Quantity", "Measure", "Stock Category", "id"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Float.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblStock.setGridColor(new java.awt.Color(255, 255, 255));
        tblStock.setOpaque(false);
        tblStock.setRowHeight(34);
        tblStock.setSelectionBackground(new java.awt.Color(0, 0, 0));
        jScrollPane2.setViewportView(tblStock);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Search:");

        txtSearchStock.setBackground(new java.awt.Color(234, 234, 234));
        txtSearchStock.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        txtSearchStock.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        txtSearchStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchStockActionPerformed(evt);
            }
        });
        txtSearchStock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchStockKeyPressed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1185, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlStockListLayout = new javax.swing.GroupLayout(pnlStockList);
        pnlStockList.setLayout(pnlStockListLayout);
        pnlStockListLayout.setHorizontalGroup(
            pnlStockListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSearchStock, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1185, Short.MAX_VALUE)
            .addComponent(jScrollPane2)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1173, Short.MAX_VALUE)
            .addComponent(jLabel5)
        );
        pnlStockListLayout.setVerticalGroup(
            pnlStockListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStockListLayout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(0, 0, 0)
                .addComponent(txtSearchStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        dynamicStockPane.add(pnlStockList, "card2");

        pnlStockCate.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 42)); // NOI18N
        jLabel6.setText("STOCK CATEGORY");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel19.setText("Name:");

        txtStockCateName.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtStockCateName.setBorder(null);
        txtStockCateName.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtStockCateName.setPreferredSize(new java.awt.Dimension(100, 36));
        txtStockCateName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtStockCateNameFocusLost(evt);
            }
        });
        txtStockCateName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStockCateNameActionPerformed(evt);
            }
        });

        jPanel20.setBackground(new java.awt.Color(0, 0, 0));
        jPanel20.setPreferredSize(new java.awt.Dimension(300, 3));

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        btnDelStockCate.setBackground(new java.awt.Color(200, 35, 51));
        btnDelStockCate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDelStockCateMouseClicked(evt);
            }
        });

        lblAdd4.setBackground(new java.awt.Color(200, 35, 51));
        lblAdd4.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblAdd4.setForeground(new java.awt.Color(255, 255, 255));
        lblAdd4.setText("DELETE");

        javax.swing.GroupLayout btnDelStockCateLayout = new javax.swing.GroupLayout(btnDelStockCate);
        btnDelStockCate.setLayout(btnDelStockCateLayout);
        btnDelStockCateLayout.setHorizontalGroup(
            btnDelStockCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnDelStockCateLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblAdd4)
                .addGap(25, 25, 25))
        );
        btnDelStockCateLayout.setVerticalGroup(
            btnDelStockCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAdd4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
        );

        txtStockCateDesc.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtStockCateDesc.setBorder(null);
        txtStockCateDesc.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtStockCateDesc.setPreferredSize(new java.awt.Dimension(100, 36));
        txtStockCateDesc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtStockCateDescFocusLost(evt);
            }
        });
        txtStockCateDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStockCateDescActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel29.setText("Description:");

        jPanel30.setBackground(new java.awt.Color(0, 0, 0));
        jPanel30.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jScrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));

        tblStockCate.setAutoCreateRowSorter(true);
        tblStockCate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        tblStockCate.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        tblStockCate.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Name", "Description", "id"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblStockCate.setGridColor(new java.awt.Color(255, 255, 255));
        tblStockCate.setRowHeight(34);
        jScrollPane3.setViewportView(tblStockCate);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Search:");

        txtSearchStockCate.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        txtSearchStockCate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        txtSearchStockCate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchStockCateKeyPressed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(0, 0, 0));
        jPanel4.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1135, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jSeparator1.setBackground(new java.awt.Color(234, 234, 234));
        jSeparator1.setForeground(new java.awt.Color(234, 234, 234));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel8.setText("List");

        btnAddStockCate.setBackground(new java.awt.Color(144, 202, 249));
        btnAddStockCate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddStockCateMouseClicked(evt);
            }
        });

        lblStockCateAdd.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblStockCateAdd.setForeground(new java.awt.Color(255, 255, 255));
        lblStockCateAdd.setText("ADD");

        javax.swing.GroupLayout btnAddStockCateLayout = new javax.swing.GroupLayout(btnAddStockCate);
        btnAddStockCate.setLayout(btnAddStockCateLayout);
        btnAddStockCateLayout.setHorizontalGroup(
            btnAddStockCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAddStockCateLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblStockCateAdd)
                .addGap(25, 25, 25))
        );
        btnAddStockCateLayout.setVerticalGroup(
            btnAddStockCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblStockCateAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnEditStockCate.setBackground(new java.awt.Color(19, 132, 150));
        btnEditStockCate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditStockCateMouseClicked(evt);
            }
        });

        lblAdd7.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblAdd7.setForeground(new java.awt.Color(255, 255, 255));
        lblAdd7.setText("EDIT");

        javax.swing.GroupLayout btnEditStockCateLayout = new javax.swing.GroupLayout(btnEditStockCate);
        btnEditStockCate.setLayout(btnEditStockCateLayout);
        btnEditStockCateLayout.setHorizontalGroup(
            btnEditStockCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnEditStockCateLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblAdd7)
                .addGap(25, 25, 25))
        );
        btnEditStockCateLayout.setVerticalGroup(
            btnEditStockCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAdd7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlStockCateLayout = new javax.swing.GroupLayout(pnlStockCate);
        pnlStockCate.setLayout(pnlStockCateLayout);
        pnlStockCateLayout.setHorizontalGroup(
            pnlStockCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStockCateLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlStockCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlStockCateLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlStockCateLayout.createSequentialGroup()
                        .addGroup(pnlStockCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlStockCateLayout.createSequentialGroup()
                                .addGroup(pnlStockCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)
                                    .addGroup(pnlStockCateLayout.createSequentialGroup()
                                        .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                                        .addGap(194, 194, 194))
                                    .addComponent(txtStockCateName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlStockCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
                                    .addComponent(txtStockCateDesc, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
                                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(btnAddStockCate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane3)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 1123, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator1)
                            .addGroup(pnlStockCateLayout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEditStockCate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelStockCate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtSearchStockCate))
                        .addGap(25, 25, 25))))
        );
        pnlStockCateLayout.setVerticalGroup(
            pnlStockCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStockCateLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel6)
                .addGap(25, 25, 25)
                .addGroup(pnlStockCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlStockCateLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(0, 0, 0)
                        .addComponent(txtStockCateName, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlStockCateLayout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addGap(0, 0, 0)
                        .addGroup(pnlStockCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlStockCateLayout.createSequentialGroup()
                                .addComponent(txtStockCateDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnAddStockCate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlStockCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlStockCateLayout.createSequentialGroup()
                        .addGroup(pnlStockCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnDelStockCate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(25, 25, 25)
                        .addComponent(jLabel7)
                        .addGap(0, 0, 0)
                        .addComponent(txtSearchStockCate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                        .addGap(25, 25, 25))
                    .addGroup(pnlStockCateLayout.createSequentialGroup()
                        .addComponent(btnEditStockCate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        dynamicStockPane.add(pnlStockCate, "card2");

        btnStockEdit.setBackground(new java.awt.Color(19, 132, 150));
        btnStockEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockEditMouseClicked(evt);
            }
        });

        btnUserIcon6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/pencil_40px.png"))); // NOI18N

        btnUserLbl6.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLbl6.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLbl6.setText("EDIT");

        javax.swing.GroupLayout btnStockEditLayout = new javax.swing.GroupLayout(btnStockEdit);
        btnStockEdit.setLayout(btnStockEditLayout);
        btnStockEditLayout.setHorizontalGroup(
            btnStockEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnStockEditLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIcon6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLbl6)
                .addGap(25, 25, 25))
        );
        btnStockEditLayout.setVerticalGroup(
            btnStockEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnStockEditLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnUserIcon6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(btnUserLbl6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnStockImport.setBackground(new java.awt.Color(144, 202, 249));
        btnStockImport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockImportMouseClicked(evt);
            }
        });

        btnUserIcon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/plus_40px.png"))); // NOI18N

        btnUserLbl4.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLbl4.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLbl4.setText("IMPORT");

        javax.swing.GroupLayout btnStockImportLayout = new javax.swing.GroupLayout(btnStockImport);
        btnStockImport.setLayout(btnStockImportLayout);
        btnStockImportLayout.setHorizontalGroup(
            btnStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnStockImportLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIcon4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLbl4)
                .addGap(25, 25, 25))
        );
        btnStockImportLayout.setVerticalGroup(
            btnStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnUserLbl4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnUserIcon4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnStockDel.setBackground(new java.awt.Color(200, 35, 51));
        btnStockDel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockDelMouseClicked(evt);
            }
        });

        btnUserIcon7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/delete_sign_40px.png"))); // NOI18N

        btnUserLbl7.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLbl7.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLbl7.setText("DELETE");

        javax.swing.GroupLayout btnStockDelLayout = new javax.swing.GroupLayout(btnStockDel);
        btnStockDel.setLayout(btnStockDelLayout);
        btnStockDelLayout.setHorizontalGroup(
            btnStockDelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnStockDelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIcon7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLbl7)
                .addGap(25, 25, 25))
        );
        btnStockDelLayout.setVerticalGroup(
            btnStockDelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnStockDelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnUserIcon7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(btnUserLbl7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnStockCate.setBackground(new java.awt.Color(0, 0, 0));
        btnStockCate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockCateMouseClicked(evt);
            }
        });

        btnUserIcon9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/category_40px.png"))); // NOI18N

        btnUserLbl9.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLbl9.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLbl9.setText("STOCK CATEGORIES");

        javax.swing.GroupLayout btnStockCateLayout = new javax.swing.GroupLayout(btnStockCate);
        btnStockCate.setLayout(btnStockCateLayout);
        btnStockCateLayout.setHorizontalGroup(
            btnStockCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnStockCateLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIcon9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLbl9)
                .addGap(25, 25, 25))
        );
        btnStockCateLayout.setVerticalGroup(
            btnStockCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnStockCateLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnUserIcon9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(btnUserLbl9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnStockList.setBackground(new java.awt.Color(0, 0, 0));
        btnStockList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockListMouseClicked(evt);
            }
        });

        btnUserIcon11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/list_40px.png"))); // NOI18N

        btnUserLbl11.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLbl11.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLbl11.setText("LIST");

        javax.swing.GroupLayout btnStockListLayout = new javax.swing.GroupLayout(btnStockList);
        btnStockList.setLayout(btnStockListLayout);
        btnStockListLayout.setHorizontalGroup(
            btnStockListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnStockListLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIcon11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLbl11)
                .addGap(25, 25, 25))
        );
        btnStockListLayout.setVerticalGroup(
            btnStockListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnUserLbl11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(btnStockListLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btnUserIcon11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout stockPanelLayout = new javax.swing.GroupLayout(stockPanel);
        stockPanel.setLayout(stockPanelLayout);
        stockPanelLayout.setHorizontalGroup(
            stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stockPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dynamicStockPane, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(stockPanelLayout.createSequentialGroup()
                        .addComponent(btnStockImport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnStockEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnStockDel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnStockCate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnStockList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );
        stockPanelLayout.setVerticalGroup(
            stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, stockPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnStockEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnStockImport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnStockDel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnStockCate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnStockList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(dynamicStockPane)
                .addGap(25, 25, 25))
        );

        dynamicPanel.add(stockPanel, "card6");

        supplierPanel.setBackground(new java.awt.Color(234, 234, 234));
        supplierPanel.setPreferredSize(new java.awt.Dimension(1217, 884));

        dynamicSupPnl.setBackground(new java.awt.Color(255, 255, 255));
        dynamicSupPnl.setLayout(new java.awt.CardLayout());

        pnlSupList.setBackground(new java.awt.Color(234, 234, 234));

        jScrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));

        tblSup.setAutoCreateRowSorter(true);
        tblSup.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        tblSup.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        tblSup.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Name", "Address", "Email", "Phone", "Company", "Branch", "id"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSup.setEditingColumn(0);
        tblSup.setEditingRow(0);
        tblSup.setGridColor(new java.awt.Color(255, 255, 255));
        tblSup.setOpaque(false);
        tblSup.setPreferredSize(new java.awt.Dimension(1167, 744));
        tblSup.setRowHeight(34);
        tblSup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSupMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tblSup);

        lblSearch2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblSearch2.setText("Search:");

        txtSearchSup.setBackground(new java.awt.Color(234, 234, 234));
        txtSearchSup.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        txtSearchSup.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        txtSearchSup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchSupActionPerformed(evt);
            }
        });
        txtSearchSup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchSupKeyPressed(evt);
            }
        });

        lineSearch2.setBackground(new java.awt.Color(0, 0, 0));
        lineSearch2.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout lineSearch2Layout = new javax.swing.GroupLayout(lineSearch2);
        lineSearch2.setLayout(lineSearch2Layout);
        lineSearch2Layout.setHorizontalGroup(
            lineSearch2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 826, Short.MAX_VALUE)
        );
        lineSearch2Layout.setVerticalGroup(
            lineSearch2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlSupListLayout = new javax.swing.GroupLayout(pnlSupList);
        pnlSupList.setLayout(pnlSupListLayout);
        pnlSupListLayout.setHorizontalGroup(
            pnlSupListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSearchSup, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
            .addComponent(jScrollPane8)
            .addComponent(lineSearch2, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
            .addComponent(lblSearch2)
        );
        pnlSupListLayout.setVerticalGroup(
            pnlSupListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSupListLayout.createSequentialGroup()
                .addComponent(lblSearch2)
                .addGap(0, 0, 0)
                .addComponent(txtSearchSup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lineSearch2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        dynamicSupPnl.add(pnlSupList, "card2");

        pnlSupAction.setBackground(new java.awt.Color(255, 255, 255));

        lblSupAction.setFont(new java.awt.Font("Segoe UI", 0, 42)); // NOI18N
        lblSupAction.setText("ADD SUPPLIER");

        lblName3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblName3.setText("Name:");

        txtSupName.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtSupName.setBorder(null);
        txtSupName.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtSupName.setPreferredSize(new java.awt.Dimension(300, 36));
        txtSupName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSupNameFocusLost(evt);
            }
        });
        txtSupName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSupNameActionPerformed(evt);
            }
        });

        LineName2.setBackground(new java.awt.Color(0, 0, 0));
        LineName2.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout LineName2Layout = new javax.swing.GroupLayout(LineName2);
        LineName2.setLayout(LineName2Layout);
        LineName2Layout.setHorizontalGroup(
            LineName2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        LineName2Layout.setVerticalGroup(
            LineName2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        btnSup.setBackground(new java.awt.Color(144, 202, 249));
        btnSup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSupMouseClicked(evt);
            }
        });

        lblSupBtn.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblSupBtn.setForeground(new java.awt.Color(255, 255, 255));
        lblSupBtn.setText("ADD");

        javax.swing.GroupLayout btnSupLayout = new javax.swing.GroupLayout(btnSup);
        btnSup.setLayout(btnSupLayout);
        btnSupLayout.setHorizontalGroup(
            btnSupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnSupLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblSupBtn)
                .addGap(25, 25, 25))
        );
        btnSupLayout.setVerticalGroup(
            btnSupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnSupLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSupBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        LineEmail2.setBackground(new java.awt.Color(0, 0, 0));
        LineEmail2.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout LineEmail2Layout = new javax.swing.GroupLayout(LineEmail2);
        LineEmail2.setLayout(LineEmail2Layout);
        LineEmail2Layout.setHorizontalGroup(
            LineEmail2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        LineEmail2Layout.setVerticalGroup(
            LineEmail2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        lbEmail2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbEmail2.setText("Email:");

        txtSupEmail.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtSupEmail.setBorder(null);
        txtSupEmail.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtSupEmail.setPreferredSize(new java.awt.Dimension(300, 36));
        txtSupEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSupEmailFocusLost(evt);
            }
        });
        txtSupEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSupEmailActionPerformed(evt);
            }
        });

        LinePhone2.setBackground(new java.awt.Color(0, 0, 0));
        LinePhone2.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout LinePhone2Layout = new javax.swing.GroupLayout(LinePhone2);
        LinePhone2.setLayout(LinePhone2Layout);
        LinePhone2Layout.setHorizontalGroup(
            LinePhone2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        LinePhone2Layout.setVerticalGroup(
            LinePhone2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        lblPhone2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblPhone2.setText("Phone:");

        txtSupPhone.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtSupPhone.setBorder(null);
        txtSupPhone.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtSupPhone.setPreferredSize(new java.awt.Dimension(300, 36));
        txtSupPhone.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSupPhoneFocusLost(evt);
            }
        });
        txtSupPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSupPhoneActionPerformed(evt);
            }
        });
        txtSupPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSupPhoneKeyPressed(evt);
            }
        });

        jScrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));

        txtSupAddr.setColumns(5);
        txtSupAddr.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtSupAddr.setRows(1);
        txtSupAddr.setTabSize(5);
        txtSupAddr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        txtSupAddr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSupAddrFocusLost(evt);
            }
        });
        jScrollPane9.setViewportView(txtSupAddr);

        LineTxtAddress2.setBackground(new java.awt.Color(0, 0, 0));
        LineTxtAddress2.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout LineTxtAddress2Layout = new javax.swing.GroupLayout(LineTxtAddress2);
        LineTxtAddress2.setLayout(LineTxtAddress2Layout);
        LineTxtAddress2Layout.setHorizontalGroup(
            LineTxtAddress2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        LineTxtAddress2Layout.setVerticalGroup(
            LineTxtAddress2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        lblAddresss2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblAddresss2.setText("Address:");

        lblName5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblName5.setText("Branch:");

        LineName4.setBackground(new java.awt.Color(0, 0, 0));
        LineName4.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout LineName4Layout = new javax.swing.GroupLayout(LineName4);
        LineName4.setLayout(LineName4Layout);
        LineName4Layout.setHorizontalGroup(
            LineName4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        LineName4Layout.setVerticalGroup(
            LineName4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        cbSupBranch.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        cbSupBranch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pnlSupActionLayout = new javax.swing.GroupLayout(pnlSupAction);
        pnlSupAction.setLayout(pnlSupActionLayout);
        pnlSupActionLayout.setHorizontalGroup(
            pnlSupActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSupActionLayout.createSequentialGroup()
                .addGroup(pnlSupActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlSupActionLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(pnlSupActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LineTxtAddress2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
                            .addComponent(LineName2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
                            .addComponent(lblName3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(LinePhone2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
                            .addComponent(txtSupPhone, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
                            .addComponent(lblPhone2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(LineEmail2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
                            .addComponent(txtSupEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbEmail2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblAddresss2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSupName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlSupActionLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(lblSupAction))
                            .addComponent(LineName4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
                            .addComponent(cbSupBranch, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblName5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(25, 25, 25))
        );
        pnlSupActionLayout.setVerticalGroup(
            pnlSupActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSupActionLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblSupAction)
                .addGap(18, 18, 18)
                .addComponent(lblName3)
                .addGap(0, 0, 0)
                .addComponent(txtSupName, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(LineName2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblName5)
                .addGap(2, 2, 2)
                .addComponent(cbSupBranch, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(LineName4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblPhone2)
                .addGap(0, 0, 0)
                .addComponent(txtSupPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(LinePhone2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbEmail2)
                .addGap(0, 0, 0)
                .addComponent(txtSupEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(LineEmail2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblAddresss2)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(LineTxtAddress2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        dynamicSupPnl.add(pnlSupAction, "card2");

        btnSupAdd.setBackground(new java.awt.Color(144, 202, 249));
        btnSupAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSupAddMouseClicked(evt);
            }
        });

        btnUserIconComAdd2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/plus_40px.png"))); // NOI18N

        btnUserLblComAdd2.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLblComAdd2.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLblComAdd2.setText("ADD");

        javax.swing.GroupLayout btnSupAddLayout = new javax.swing.GroupLayout(btnSupAdd);
        btnSupAdd.setLayout(btnSupAddLayout);
        btnSupAddLayout.setHorizontalGroup(
            btnSupAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnSupAddLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIconComAdd2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLblComAdd2)
                .addGap(25, 25, 25))
        );
        btnSupAddLayout.setVerticalGroup(
            btnSupAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnUserLblComAdd2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnUserIconComAdd2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnSupEdit.setBackground(new java.awt.Color(19, 132, 150));
        btnSupEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSupEditMouseClicked(evt);
            }
        });

        btnUserIconComEdit2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/pencil_40px.png"))); // NOI18N

        btnUserLblComEdit2.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLblComEdit2.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLblComEdit2.setText("EDIT");

        javax.swing.GroupLayout btnSupEditLayout = new javax.swing.GroupLayout(btnSupEdit);
        btnSupEdit.setLayout(btnSupEditLayout);
        btnSupEditLayout.setHorizontalGroup(
            btnSupEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnSupEditLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIconComEdit2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLblComEdit2)
                .addGap(25, 25, 25))
        );
        btnSupEditLayout.setVerticalGroup(
            btnSupEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnSupEditLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnUserIconComEdit2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(btnUserLblComEdit2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnSupDel.setBackground(new java.awt.Color(200, 35, 51));
        btnSupDel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSupDelMouseClicked(evt);
            }
        });

        btnUserIconDel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/delete_sign_40px.png"))); // NOI18N

        btnUserLblDel2.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLblDel2.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLblDel2.setText("DELETE");

        javax.swing.GroupLayout btnSupDelLayout = new javax.swing.GroupLayout(btnSupDel);
        btnSupDel.setLayout(btnSupDelLayout);
        btnSupDelLayout.setHorizontalGroup(
            btnSupDelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnSupDelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIconDel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLblDel2)
                .addGap(25, 25, 25))
        );
        btnSupDelLayout.setVerticalGroup(
            btnSupDelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnSupDelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnUserIconDel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(btnUserLblDel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnSupList.setBackground(new java.awt.Color(0, 0, 0));
        btnSupList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSupListMouseClicked(evt);
            }
        });

        btnUserIconComList2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/list_40px.png"))); // NOI18N

        btnUserLblComList2.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLblComList2.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLblComList2.setText("LIST");

        javax.swing.GroupLayout btnSupListLayout = new javax.swing.GroupLayout(btnSupList);
        btnSupList.setLayout(btnSupListLayout);
        btnSupListLayout.setHorizontalGroup(
            btnSupListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnSupListLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIconComList2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLblComList2)
                .addGap(25, 25, 25))
        );
        btnSupListLayout.setVerticalGroup(
            btnSupListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnUserLblComList2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(btnSupListLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btnUserIconComList2, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout supplierPanelLayout = new javax.swing.GroupLayout(supplierPanel);
        supplierPanel.setLayout(supplierPanelLayout);
        supplierPanelLayout.setHorizontalGroup(
            supplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 876, Short.MAX_VALUE)
            .addGroup(supplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(supplierPanelLayout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addGroup(supplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(dynamicSupPnl, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(supplierPanelLayout.createSequentialGroup()
                            .addComponent(btnSupAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnSupEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnSupDel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSupList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(25, 25, 25)))
        );
        supplierPanelLayout.setVerticalGroup(
            supplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 876, Short.MAX_VALUE)
            .addGroup(supplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(supplierPanelLayout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addGroup(supplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnSupEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSupAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSupDel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSupList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addComponent(dynamicSupPnl)
                    .addGap(25, 25, 25)))
        );

        dynamicPanel.add(supplierPanel, "card2");

        exitIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/icons8_delete_sign_50px.png"))); // NOI18N
        exitIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitIconMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addComponent(sidebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dynamicPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dynamicLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(exitIcon)
                        .addGap(5, 5, 5))))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sidebar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(exitIcon)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(dynamicLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(dynamicPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 881, Short.MAX_VALUE))
        );

        getContentPane().add(background, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 1289, 939);
    }// </editor-fold>//GEN-END:initComponents

    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        com = false;
        bra = false;
        prod = false;
        stocks = false;
        staffs = false;
        user = false;
        dash = true;
        sup = false;
        
        //dynamicPanel
        dynamicPanel.removeAll();
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicPanel.add(dashPanel);
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicLabel.setFont(new java.awt.Font("Segoe UI", 0, 36));
        dynamicLabel.setForeground(new java.awt.Color(255, 255, 255));
        dynamicLabel.setText("DASHBOARD");
        
        //braLayer
        branchLabel.setBackground(new Color(78, 45, 17));
        braActive.setBackground(new Color(78, 45, 17));
        
        //comLayer
        comLabel.setBackground(new Color(78, 45, 17));
        comActive.setBackground(new Color(78, 45, 17));
        
        //proLayer
        proLabel.setBackground(new Color(78, 45, 17));
        proActive.setBackground(new Color(78, 45, 17));
        
        //staffLayer
        staffLabel.setBackground(new Color(78, 45, 17));
        staffActive.setBackground(new Color(78, 45, 17));
        
        stockLabel.setBackground(new Color(78, 45, 17));
        stockActive.setBackground(new Color(78, 45, 17));
        
        userPnl.setBackground(new Color(78, 45, 17));
        userActive.setBackground(new Color(78, 45, 17));
        
        supplierPnl.setBackground(new Color(78, 45, 17));
        supplierActive.setBackground(new Color(78, 45, 17));
        
        dashPnl.setBackground(new Color(102, 51, 0));
        dashLayp.add(dashBlink, 1);
        dashLayp.add(dashActive, 0);
        dashActive.setBackground(new Color(255, 255, 255));
        dashPnl.setBackground(new Color(102, 51, 0));
    }//GEN-LAST:event_formWindowOpened

    private void exitIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitIconMouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_exitIconMouseClicked

    private void comLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comLabelMouseClicked
        // TODO add your handling code here:
        com = true;
        bra = false;
        prod = false;
        stocks = false;
        staffs = false;
        user = false;
        dash = false;
        sup = false;
        
        //dyanmicPanel
        dynamicPanel.removeAll();
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicPanel.add(comPanel);
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicLabel.setFont(new Font("Segoe UI", 0, 36)); 
        dynamicLabel.setForeground(new java.awt.Color(255, 255, 255));
        dynamicLabel.setText("COMPANY");
        showCompanies(getComList());
        JTableHeader header = tblCom.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.setOpaque(false);
        header.setForeground(Color.WHITE);
        header.setBackground(Color.black);
        
        //braLayer
        braActive.setBackground(new Color(78, 45, 17));
        branchLabel.setBackground(new Color(78, 45, 17));

        //proLayer
        proActive.setBackground(new Color(78, 45, 17));
        proLabel.setBackground(new Color(78, 45, 17));
        
        //stockLayer
        stockLabel.setBackground(new Color(78, 45, 17));
        stockActive.setBackground(new Color(78, 45, 17));
        
        //staffLayer
        staffActive.setBackground(new Color(78, 45, 17));
        staffLabel.setBackground(new Color(78, 45, 17));
        
        userPnl.setBackground(new Color(78, 45, 17));
        userActive.setBackground(new Color(78, 45, 17));
        
        dashPnl.setBackground(new Color(78, 45, 17));
        dashActive.setBackground(new Color(78, 45, 17));
        
        supplierPnl.setBackground(new Color(78, 45, 17));
        supplierActive.setBackground(new Color(78, 45, 17));
        
        //comLayer
        comLayer.removeAll();
        comLayer.add(comActive, 0);
        comLayer.add(comBlink, 1);
        comActive.setBackground(new Color(255, 255, 255));
        comLabel.setBackground(new Color(102, 51, 0));
    }//GEN-LAST:event_comLabelMouseClicked

    private void comLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comLabelMouseEntered
        // TODO add your handling code here:
        if(com == false){
            comLayer.add(comActive, 1);
            comLayer.add(comBlink, 0);
            comBlink.setBackground(new Color(255, 255, 255));
        }
        
    }//GEN-LAST:event_comLabelMouseEntered

    private void comLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comLabelMouseExited
        // TODO add your handling code here:
        if(com == false)
            comBlink.setBackground(new Color(78, 45, 17));
    }//GEN-LAST:event_comLabelMouseExited

    private void branchLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_branchLabelMouseClicked
        // TODO add your handling code here:
        com = false;
        bra = true;
        prod = false;
        stocks = false;
        staffs = false;
        user = false;
        dash = false;
        sup = false;
        
        //dynamicPanel
        dynamicPanel.removeAll();
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicPanel.add(branchPanel);
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicLabel.setFont(new java.awt.Font("Segoe UI", 0, 36));
        dynamicLabel.setForeground(new java.awt.Color(255, 255, 255));
        dynamicLabel.setText("BRANCH");
        showBranches(getBranchList());
        JTableHeader header = tblBranch.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.setOpaque(false);
        header.setForeground(Color.WHITE);
        header.setBackground(Color.black);
        
        //comLayer
        comActive.setBackground(new Color(78, 45, 17));
        comLabel.setBackground(new Color(78, 45, 17));
        
        //proLayer
        proActive.setBackground(new Color(78, 45, 17));
        proLabel.setBackground(new Color(78, 45, 17));
        
        //stafffLayer
        staffLabel.setBackground(new Color(78, 45, 17));
        staffActive.setBackground(new Color(78, 45, 17));
        
        //stockLayer
        stockLabel.setBackground(new Color(78, 45, 17));
        stockActive.setBackground(new Color(78, 45, 17));
        
        userPnl.setBackground(new Color(78, 45, 17));
        userActive.setBackground(new Color(78, 45, 17));
        
        dashPnl.setBackground(new Color(78, 45, 17));
        dashActive.setBackground(new Color(78, 45, 17));
        
        supplierPnl.setBackground(new Color(78, 45, 17));
        supplierActive.setBackground(new Color(78, 45, 17));
        
        //braLayer
        braLayer.removeAll();       
        braLayer.add(braActive, 0);
        braLayer.add(braBlink, 1);
        braActive.setBackground(new Color(255, 255, 255));
        branchLabel.setBackground(new Color(102, 51, 0));
    }//GEN-LAST:event_branchLabelMouseClicked

    private void branchLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_branchLabelMouseEntered
        // TODO add your handling code here:
        if(bra == false){
            braLayer.add(braActive, 1);
            braLayer.add(braBlink, 0);
            braBlink.setBackground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_branchLabelMouseEntered

    private void branchLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_branchLabelMouseExited
        // TODO add your handling code here:
        if(bra == false)
            braBlink.setBackground(new Color(78, 45, 17)); 
    }//GEN-LAST:event_branchLabelMouseExited

    private void proLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proLabelMouseClicked
        // TODO add your handling code here:
        com = false;
        bra = false;
        prod = true;
        stocks = false;
        staffs = false;
        user = false;
        dash = false;
        sup = false;
        
        //dynamicPanel
        dynamicPanel.removeAll();
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicPanel.add(proPanel);
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicLabel.setFont(new java.awt.Font("Segoe UI", 0, 36));
        dynamicLabel.setForeground(new java.awt.Color(255, 255, 255));
        dynamicLabel.setText("PRODUCT");
        showProductVar(getAllProductVars());
        JTableHeader header = tblPro.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.setOpaque(false);
        header.setForeground(Color.WHITE);
        header.setBackground(Color.black);
        
        dynamicProPane.removeAll();
        dynamicProPane.repaint();
        dynamicProPane.revalidate();
        
        cbProCate.removeAllItems();
        AppHelper.getCombos("name", "product_categories")
            .forEach((r) -> cbProCate.addItem(AppHelper.toCapitalize(r)));
        cbProBranch.removeAllItems();
        AppHelper.getCombos("name", "branches", "user_id", userId)
            .forEach((r) -> cbProBranch.addItem(r));
        
        //comLayer
        comLabel.setBackground(new Color(78, 45, 17));
        
        //braLayer
        braActive.setBackground(new Color(78, 45, 17));
        branchLabel.setBackground(new Color(78, 45, 17));

        //comLayer
        comActive.setBackground(new Color(78, 45, 17));
        
        //stockLayer
        stockLabel.setBackground(new Color(78, 45, 17));
        stockActive.setBackground(new Color(78, 45, 17));
        
        //staffLayer
        staffLabel.setBackground(new Color(78, 45, 17));
        staffActive.setBackground(new Color(78, 45, 17));
        
        userPnl.setBackground(new Color(78, 45, 17));
        userActive.setBackground(new Color(78, 45, 17));
        
        dashPnl.setBackground(new Color(78, 45, 17));
        dashActive.setBackground(new Color(78, 45, 17));
        
        supplierPnl.setBackground(new Color(78, 45, 17));
        supplierActive.setBackground(new Color(78, 45, 17));
        
        //proLayer
        proLayer.removeAll();
        proLayer.add(proActive, 0);
        proLayer.add(proBlink, 1);
        proActive.setBackground(new Color(255, 255, 255));
        proLabel.setBackground(new Color(102, 51, 0));
    }//GEN-LAST:event_proLabelMouseClicked

    private void proLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proLabelMouseEntered
        // TODO add your handling code here:
        if(prod == false){
            proLayer.add(proActive, 1);
            proLayer.add(proBlink, 0);
            proBlink.setBackground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_proLabelMouseEntered

    private void proLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proLabelMouseExited
        // TODO add your handling code here:
        if(prod == false)
            proBlink.setBackground(new Color(78, 45, 17));
    }//GEN-LAST:event_proLabelMouseExited

    private void staffLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_staffLabelMouseClicked
        // TODO add your handling code here:
        com = false;
        bra = false;
        prod = false;
        stocks = false;
        staffs = true;
        user = false;
        dash = false;
        sup = false;
        
        //dynamicPanel
        dynamicPanel.removeAll();
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicPanel.add(branchPanel);
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicLabel.setFont(new java.awt.Font("Segoe UI", 0, 36));
        dynamicLabel.setForeground(new java.awt.Color(255, 255, 255));
        dynamicLabel.setText("STAFF");
        
        //braLayer
        branchLabel.setBackground(new Color(78, 45, 17));
        braActive.setBackground(new Color(78, 45, 17));
        
        //comLayer
        comLabel.setBackground(new Color(78, 45, 17));
        comActive.setBackground(new Color(78, 45, 17));
        
        //proLayer
        proLabel.setBackground(new Color(78, 45, 17));
        proActive.setBackground(new Color(78, 45, 17));
        
        dashPnl.setBackground(new Color(78, 45, 17));
        dashActive.setBackground(new Color(78, 45, 17));
        
        //stockLayer
        stockLabel.setBackground(new Color(78, 45, 17));
        stockActive.setBackground(new Color(78, 45, 17));
        
        userPnl.setBackground(new Color(78, 45, 17));
        userActive.setBackground(new Color(78, 45, 17));
        
        supplierPnl.setBackground(new Color(78, 45, 17));
        supplierActive.setBackground(new Color(78, 45, 17));
        
        //staffLayer
        staffLayer.add(staffBlink, 1);
        staffLayer.add(staffActive, 0);
        staffActive.setBackground(new Color(255, 255, 255));
        staffLabel.setBackground(new Color(102, 51, 0));
    }//GEN-LAST:event_staffLabelMouseClicked

    private void staffLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_staffLabelMouseEntered
        // TODO add your handling code here:
        if(staffs == false){
            staffLayer.add(staffActive, 1);
            staffLayer.add(staffBlink, 0);
            staffBlink.setBackground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_staffLabelMouseEntered

    private void staffLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_staffLabelMouseExited
        // TODO add your handling code here:
        if(staffs == false)
            staffBlink.setBackground(new Color(78, 45, 17));
    }//GEN-LAST:event_staffLabelMouseExited

    private void stockLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stockLabelMouseClicked
        // TODO add your handling code here:
        com = false;
        bra = false;
        prod = false;
        stocks = true;
        staffs = false;
        user = false;
        dash = false;
        sup = false;
        
        //dynamicPanel
        dynamicPanel.removeAll();
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicPanel.add(stockPanel);
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicLabel.setFont(new java.awt.Font("Segoe UI", 0, 36));
        dynamicLabel.setForeground(new java.awt.Color(255, 255, 255));
        dynamicLabel.setText("STOCK");
        
        dynamicStockPane.removeAll();
        dynamicStockPane.repaint();
        dynamicStockPane.revalidate();
        dynamicStockPane.add(pnlStockList);
        dynamicStockPane.repaint();
        dynamicStockPane.revalidate();
        
        showStocks(getAllStocks());
        
        JTableHeader header = tblStock.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.setOpaque(false);
        header.setForeground(Color.WHITE);
        header.setBackground(Color.black);
        
        //braLayer
        branchLabel.setBackground(new Color(78, 45, 17));
        braActive.setBackground(new Color(78, 45, 17));
        
        //comLayer
        comLabel.setBackground(new Color(78, 45, 17));
        comActive.setBackground(new Color(78, 45, 17));
        
        //proLayer
        proLabel.setBackground(new Color(78, 45, 17));
        proActive.setBackground(new Color(78, 45, 17));
        
        //staffLayer
        staffLabel.setBackground(new Color(78, 45, 17));
        staffActive.setBackground(new Color(78, 45, 17));
        
        userPnl.setBackground(new Color(78, 45, 17));
        userActive.setBackground(new Color(78, 45, 17));
        
        dashPnl.setBackground(new Color(78, 45, 17));
        dashActive.setBackground(new Color(78, 45, 17));
        
        supplierPnl.setBackground(new Color(78, 45, 17));
        supplierActive.setBackground(new Color(78, 45, 17));
        
        //stockLayer
        stockLabel.setBackground(new Color(102, 51, 0));
        stockLayer.add(stockBlink, 1);
        stockLayer.add(stockActive, 0);
        stockActive.setBackground(new Color(255, 255, 255));
        stockLabel.setBackground(new Color(102, 51, 0));
    }//GEN-LAST:event_stockLabelMouseClicked

    private void stockLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stockLabelMouseEntered
        // TODO add your handling code here:
        if(stocks == false){
            stockLayer.add(stockActive, 1);
            stockLayer.add(stockBlink, 0);
            stockBlink.setBackground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_stockLabelMouseEntered

    private void stockLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stockLabelMouseExited
        // TODO add your handling code here:
        if(stocks == false)
            stockBlink.setBackground(new Color(78, 45, 17));
    }//GEN-LAST:event_stockLabelMouseExited

    private void userPnlMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userPnlMouseClicked
        // TODO add your handling code here:
        com = false;
        bra = false;
        prod = false;
        stocks = false;
        staffs = false;
        user = true;
        dash = false;
        sup = false;

        //dynamicPanel
        dynamicPanel.removeAll();
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicPanel.add(userPanel);
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicLabel.setFont(new java.awt.Font("Segoe UI", 0, 36));
        dynamicLabel.setForeground(new java.awt.Color(255, 255, 255));
        dynamicLabel.setText("USER");
        showUsers(getAllUsers());
        JTableHeader header = userTbl.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.setOpaque(false);
        header.setForeground(Color.WHITE);
        header.setBackground(Color.black);

        //braLayer
        branchLabel.setBackground(new Color(78, 45, 17));
        braActive.setBackground(new Color(78, 45, 17));

        //comLayer
        comLabel.setBackground(new Color(78, 45, 17));
        comActive.setBackground(new Color(78, 45, 17));

        //proLayer
        proLabel.setBackground(new Color(78, 45, 17));
        proActive.setBackground(new Color(78, 45, 17));

        //staffLayer
        staffLabel.setBackground(new Color(78, 45, 17));
        staffActive.setBackground(new Color(78, 45, 17));

        stockLabel.setBackground(new Color(78, 45, 17));
        stockActive.setBackground(new Color(78, 45, 17));

        dashPnl.setBackground(new Color(78, 45, 17));
        dashActive.setBackground(new Color(78, 45, 17));

        supplierPnl.setBackground(new Color(78, 45, 17));
        supplierActive.setBackground(new Color(78, 45, 17));

        userPnl.setBackground(new Color(102, 51, 0));
        userLayp.add(userBlink, 1);
        userLayp.add(userActive, 0);
        userActive.setBackground(new Color(255, 255, 255));
        userPnl.setBackground(new Color(102, 51, 0));
    }//GEN-LAST:event_userPnlMouseClicked

    private void userPnlMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userPnlMouseEntered
        // TODO add your handling code here:
        if(user == false){
            userLayp.add(userActive, 1);
            userLayp.add(userBlink, 0);
            userBlink.setBackground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_userPnlMouseEntered

    private void userPnlMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userPnlMouseExited
        // TODO add your handling code here:
        if(user == false)
            userBlink.setBackground(new Color(78, 45, 17));
    }//GEN-LAST:event_userPnlMouseExited

    private void dashPnlMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashPnlMouseClicked
        // TODO add your handling code here:
        com = false;
        bra = false;
        prod = false;
        stocks = false;
        staffs = false;
        user = false;
        dash = true;
        sup = false;
        
        //dynamicPanel
        dynamicPanel.removeAll();
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicPanel.add(dashPanel);
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicLabel.setFont(new java.awt.Font("Segoe UI", 0, 36));
        dynamicLabel.setForeground(new java.awt.Color(255, 255, 255));
        dynamicLabel.setText("DASHBOARD");
        
        //braLayer
        branchLabel.setBackground(new Color(78, 45, 17));
        braActive.setBackground(new Color(78, 45, 17));
        
        //comLayer
        comLabel.setBackground(new Color(78, 45, 17));
        comActive.setBackground(new Color(78, 45, 17));
        
        //proLayer
        proLabel.setBackground(new Color(78, 45, 17));
        proActive.setBackground(new Color(78, 45, 17));
        
        //staffLayer
        staffLabel.setBackground(new Color(78, 45, 17));
        staffActive.setBackground(new Color(78, 45, 17));
        
        stockLabel.setBackground(new Color(78, 45, 17));
        stockActive.setBackground(new Color(78, 45, 17));
        
        userPnl.setBackground(new Color(78, 45, 17));
        userActive.setBackground(new Color(78, 45, 17));
        
        supplierPnl.setBackground(new Color(78, 45, 17));
        supplierActive.setBackground(new Color(78, 45, 17));
        
        dashPnl.setBackground(new Color(102, 51, 0));
        dashLayp.add(dashBlink, 1);
        dashLayp.add(dashActive, 0);
        dashActive.setBackground(new Color(255, 255, 255));
        dashPnl.setBackground(new Color(102, 51, 0));
    }//GEN-LAST:event_dashPnlMouseClicked

    private void dashPnlMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashPnlMouseEntered
        // TODO add your handling code here:
        if(dash == false){
            dashLayp.add(dashActive, 1);
            dashLayp.add(dashBlink, 0);
            dashBlink.setBackground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_dashPnlMouseEntered

    private void dashPnlMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashPnlMouseExited
        // TODO add your handling code here:
        if(dash == false)
            dashBlink.setBackground(new Color(78, 45, 17));
    }//GEN-LAST:event_dashPnlMouseExited

    private void btnRoleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRoleMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "roles", "read"))
            AppHelper.permissionMessage();
        else
            new RoleForm().setVisible(true);
    }//GEN-LAST:event_btnRoleMouseClicked

    private void btnPermissionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPermissionMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "permissions", "read"))
            AppHelper.permissionMessage();
        else
            new PermissionForm().setVisible(true);
    }//GEN-LAST:event_btnPermissionMouseClicked

    private void btnAddUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddUserMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "users", "create"))
            AppHelper.permissionMessage();
        else
            new UserActions().setVisible(true);
    }//GEN-LAST:event_btnAddUserMouseClicked

    private void btnEditUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditUserMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "users", "update"))
            AppHelper.permissionMessage();
        else {
            int row = userTbl.getSelectedRow();
            if(row < 0)
                JOptionPane.showMessageDialog(null, "Please select any user first to edit!");
            else {
                int id = (int)userTbl.getValueAt(row, 6);
                new UserActions("edit", id).setVisible(true);   
            }
        }
    }//GEN-LAST:event_btnEditUserMouseClicked

    private void btnChangePassMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnChangePassMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "users", "update"))
            AppHelper.permissionMessage();
        else {
            int row = userTbl.getSelectedRow();
            if(row < 0)
                JOptionPane.showMessageDialog(null, "Please select any user first to edit!");
            else {
                int id = (int)userTbl.getValueAt(row, 6);
                new UserActions("edit_password", id).setVisible(true);   
            }
        }
    }//GEN-LAST:event_btnChangePassMouseClicked

    private void btnDelUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDelUserMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "users", "delete"))
            AppHelper.permissionMessage();
        else {
            int row = userTbl.getSelectedRow();
            if(row < 0)
                JOptionPane.showMessageDialog(null, "Please select any user first to edit!");
            else {
                int id = (int)userTbl.getValueAt(row, 6);
                if(id == userId) {
                    JOptionPane.showMessageDialog(null, "You can't delete yourself!");
                } else {
                    int res = JOptionPane.showConfirmDialog(
                        this, 
                        "Are you sure you want to delete this?", 
                        "Confirm message", 
                        JOptionPane.YES_NO_OPTION, 
                        JOptionPane.QUESTION_MESSAGE
                    );
                    if(res == JOptionPane.YES_OPTION) {
                        User myUser = new User();
                        myUser.delete(id);
                        showUsers(getAllUsers());
                    }
                }
            }
        }
    }//GEN-LAST:event_btnDelUserMouseClicked

    private void btnUserRefreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUserRefreshMouseClicked
        // TODO add your handling code here:
        showUsers(getAllUsers());
    }//GEN-LAST:event_btnUserRefreshMouseClicked

    private void btnLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseClicked
        // TODO add your handling code here:
        this.dispose();
        new Login().setVisible(true);
        JOptionPane.showMessageDialog(null, "You have logged out!");
    }//GEN-LAST:event_btnLogoutMouseClicked

    private void supplierPnlMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_supplierPnlMouseClicked
        // TODO add your handling code here:
        com = false;
        bra = false;
        prod = false;
        stocks = false;
        staffs = false;
        user = false;
        dash = true;
        sup = false;
        
        //dynamicPanel
        dynamicPanel.removeAll();
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicPanel.add(supplierPanel);
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicLabel.setFont(new java.awt.Font("Segoe UI", 0, 36));
        dynamicLabel.setForeground(new java.awt.Color(255, 255, 255));
        dynamicLabel.setText("SUPPLIER");
        showSup(getSupList());
        JTableHeader header = tblSup.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.setOpaque(false);
        header.setForeground(Color.WHITE);
        header.setBackground(Color.black);
        
        //braLayer
        branchLabel.setBackground(new Color(78, 45, 17));
        braActive.setBackground(new Color(78, 45, 17));
        
        //comLayer
        comLabel.setBackground(new Color(78, 45, 17));
        comActive.setBackground(new Color(78, 45, 17));
        
        //proLayer
        proLabel.setBackground(new Color(78, 45, 17));
        proActive.setBackground(new Color(78, 45, 17));
        
        //staffLayer
        staffLabel.setBackground(new Color(78, 45, 17));
        staffActive.setBackground(new Color(78, 45, 17));
        
        stockLabel.setBackground(new Color(78, 45, 17));
        stockActive.setBackground(new Color(78, 45, 17));
        
        userPnl.setBackground(new Color(78, 45, 17));
        userActive.setBackground(new Color(78, 45, 17));
        
        dashPnl.setBackground(new Color(78, 45, 17));
        dashActive.setBackground(new Color(78, 45, 17));
        
        supplierPnl.setBackground(new Color(102, 51, 0));
        supplierLayp.add(supplierBlink, 1);
        supplierLayp.add(supplierActive, 0);
        supplierActive.setBackground(new Color(255, 255, 255));
        supplierPnl.setBackground(new Color(102, 51, 0));
    }//GEN-LAST:event_supplierPnlMouseClicked

    private void supplierPnlMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_supplierPnlMouseEntered
        // TODO add your handling code here:
        if(sup == false){
            supplierLayp.add(supplierActive, 1);
            supplierLayp.add(supplierBlink, 0);
            supplierBlink.setBackground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_supplierPnlMouseEntered

    private void supplierPnlMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_supplierPnlMouseExited
        // TODO add your handling code here:
        if(sup == false)
            supplierBlink.setBackground(new Color(78, 45, 17));
    }//GEN-LAST:event_supplierPnlMouseExited

    private void txtSearchUserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchUserKeyPressed
        // TODO add your handling code here:
        if(AppHelper.alphOnly(evt))
            txtSearchUser.setEditable(true);
        else
            txtSearchUser.setEditable(false);
        showUsers(searchUser(txtSearchUser.getText().trim()));
    }//GEN-LAST:event_txtSearchUserKeyPressed

    private void btnStockImportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockImportMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "stocks", "create"))
            AppHelper.permissionMessage();
        else {
            dynamicStockPane.removeAll();
            dynamicStockPane.repaint();
            dynamicStockPane.revalidate();
            dynamicStockPane.add(pnlStockImport);
            dynamicStockPane.repaint();
            dynamicStockPane.revalidate();
            lblStockAction.setText("IMPORT STOCK");
            btnStock.setBackground(new Color(144, 202, 249));
            lblStockBtn.setText("ADD");
            cbStockCate.removeAllItems();
            AppHelper.getCombos("name", "stock_categories")
                .forEach((r) -> cbStockCate.addItem(AppHelper.toCapitalize(r)));
            cbStockCompany.removeAllItems();
            AppHelper.getCombos("name", "companies", "user_id", userId)
                .forEach((r) -> cbStockCompany.addItem(r));
            cbStockBranch.removeAllItems();
            AppHelper.getCombos("name", "branches", "user_id", userId)
                .forEach((r) -> cbStockBranch.addItem(r));
            cbStockSupplier.removeAllItems();
            AppHelper.getCombos("name", "suppliers", "user_id", userId)
                .forEach((r) -> cbStockSupplier.addItem(r));
            txtStockName.setText("");
            dpStockExpired.setDate(null);
            txtStockQty.setText("");
            cbStockCate.setSelectedIndex(0);
            cbStockMeasure.setSelectedIndex(0);
            txtStockAlertQty.setText("");
            txtStockPrice.setText(""); 
        }
    }//GEN-LAST:event_btnStockImportMouseClicked

    private void btnStockEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockEditMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "stocks", "update"))
            AppHelper.permissionMessage();
        else {
            int row = tblStock.getSelectedRow();
            if(row < 0)
                JOptionPane.showMessageDialog(null, "Please select any stock first to edit!");
            else {
                int id = (int)tblStock.getValueAt(row, 5);
                dynamicStockPane.removeAll();
                dynamicStockPane.repaint();
                dynamicStockPane.revalidate();
                dynamicStockPane.add(pnlStockImport);
                dynamicStockPane.repaint();
                dynamicStockPane.revalidate();
                lblStockAction.setText("EDIT STOCK");
                btnStock.setBackground(new Color(19, 132, 150));
                lblStockBtn.setText("EDIT");
                cbStockCate.removeAllItems();
                AppHelper.getCombos("name", "stock_categories")
                    .forEach((r) -> cbStockCate.addItem(AppHelper.toCapitalize(r)));
                cbStockCompany.removeAllItems();
                AppHelper.getCombos("name", "companies", "user_id", userId)
                    .forEach((r) -> cbStockCompany.addItem(r));
                cbStockBranch.removeAllItems();
                AppHelper.getCombos("name", "branches", "user_id", userId)
                    .forEach((r) -> cbStockBranch.addItem(r));
                cbStockSupplier.removeAllItems();
                AppHelper.getCombos("name", "suppliers", "user_id", userId)
                    .forEach((r) -> cbStockSupplier.addItem(r));
                String sql = "SELECT `import_details`.`price` AS `import_price`, " +
                    "`imports`.`supplier_id`, " +
                    "`stocks`.*, " +
                    "`stock_categories`.`name` AS stock_cate_name " +
                    "FROM `import_details` " +
                    "INNER JOIN `imports` " +
                    "ON `import_details`.`import_id` = `imports`.`id` " +
                    "INNER JOIN `stocks` " +
                    "ON `import_details`.`stock_id` = `stocks`.`id` " +
                    "INNER JOIN `stock_categories` " +
                    "ON `stocks`.`stock_category_id` = `stock_categories`.`id` " +
                    "WHERE `stocks`.`id` = ?";                
                try {
                    ResultSet rs = AppHelper.selectQuery(sql, id);
                    if(rs.next()) {
                        txtStockName.setText(rs.getString("name"));
                        dpStockExpired.setDate(rs.getDate("expired_date"));
                        txtStockQty.setText(rs.getString("qty"));
                        cbStockCate.setSelectedItem(rs.getString("stock_cate_name"));
                        cbStockMeasure.setSelectedItem(rs.getString("measure_unit"));
                        txtStockAlertQty.setText(rs.getString("alert_qty"));
                        txtStockPrice.setText(rs.getString("import_price"));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Main_Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }    
    }//GEN-LAST:event_btnStockEditMouseClicked

    private void btnStockDelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockDelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnStockDelMouseClicked

    private void btnStockCateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockCateMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "stock_categories", "read")){
            AppHelper.permissionMessage();
        } else {
            dynamicStockPane.removeAll();
            dynamicStockPane.repaint();
            dynamicStockPane.revalidate();
            dynamicStockPane.add(pnlStockCate);
            dynamicStockPane.repaint();
            dynamicStockPane.revalidate();

            JTableHeader header = tblStockCate.getTableHeader();
            header.setFont(new Font("Segoe UI", Font.BOLD, 26));
            header.setOpaque(false);
            header.setForeground(Color.WHITE);
            header.setBackground(Color.black);
            showStockCate(getAllStockCate());
            txtStockCateName.requestFocus();
        }
    }//GEN-LAST:event_btnStockCateMouseClicked

    private void txtSearchStockKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchStockKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchStockKeyPressed

    private void txtStockNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStockNameFocusLost
        // TODO add your handling code here:
        int max = 25;
        if(!txtStockName.getText().trim().equals("") && !AppHelper.isMatchLength("max", max, txtStockName.getText().trim().length())) {
            AppHelper.errorMessage("name", max);
            txtStockName.requestFocus();
        }
    }//GEN-LAST:event_txtStockNameFocusLost

    private void txtStockNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStockNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockNameActionPerformed

    private void txtStockQtyFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStockQtyFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockQtyFocusLost

    private void txtStockQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStockQtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockQtyActionPerformed

    private void txtStockAlertQtyFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStockAlertQtyFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockAlertQtyFocusLost

    private void txtStockAlertQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStockAlertQtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockAlertQtyActionPerformed

    private void txtStockPriceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStockPriceFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockPriceFocusLost

    private void txtStockPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStockPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockPriceActionPerformed

    private void btnStockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockMouseClicked
        // TODO add your handling code here:
        if(
            txtStockName.getText().trim().equals("") || 
            dpStockExpired.getDateFormatString().trim().equals("") || 
            txtStockQty.getText().trim().equals("") ||
            txtStockPrice.getText().trim().equals("") ||
            txtStockAlertQty.getText().trim().equals("")
        )
        {
            AppHelper.fieldRequiredMsg();
            txtStockName.requestFocus();
        } else {
            String name = txtStockName.getText().trim();
            Date expiredDate = dpStockExpired.getDate();
            double qty = Double.valueOf(txtStockQty.getText().trim());
            int alertQty = Integer.valueOf(txtStockAlertQty.getText().trim());
            double price = Double.valueOf(txtStockPrice.getText().trim());
            Date currentDate = new Date();
            
            DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            
            if(expiredDate.compareTo(currentDate) > 0) {
                int scId = AppHelper.getId(
                    String.valueOf(cbStockCate.getSelectedItem()), 
                    "id", 
                    "stock_categories", 
                    "name"
                );
                int cId = AppHelper.getId(
                    String.valueOf(cbStockCompany.getSelectedItem()), 
                    "id", 
                    "companies", 
                    "name"
                );
                int bId = AppHelper.getId(
                    String.valueOf(cbStockBranch.getSelectedItem()), 
                    "id", 
                    "branches", 
                    "name"
                );
                myStock.setName(name);            
                myStock.setExpiredDate(df.format(expiredDate));
                myStock.setQty(qty);
                myStock.setAlertQty(alertQty);
                myStock.setAlerted(0);
                myStock.setMeasureUnit(String.valueOf(cbStockMeasure.getSelectedItem()));
                myStock.setStockCateId(scId);
                myStock.setCompanyId(cId);
                myStock.setBranchId(bId);
                myStock.insert();

                if(myStock.isInserted()) {
                    int sId = AppHelper.getId(
                        String.valueOf(cbStockSupplier.getSelectedItem()), 
                        "id", 
                        "suppliers", 
                        "name"
                    );
                    myImport.setCreatedDate(dtf.format(currentDate));
                    myImport.setUpdatedDate(dtf.format(currentDate));
                    myImport.setUserId(userId);
                    myImport.setSupplierId(sId);
                    myImport.insert();
                    
                    if(myImport.isInserted()) {
                        myImpDetail.setImportId(myImport.getId());
                        myImpDetail.setStockId(myStock.getId());
                        myImpDetail.setQty(qty);
                        myImpDetail.setPrice(price);
                        myImpDetail.insert();
                        
                        if(myImpDetail.isInserted()) {
                            JOptionPane.showMessageDialog(null, myImpDetail.getMessage());
                            txtStockName.setText("");
                            txtStockQty.setText("");
                            txtStockPrice.setText("");
                            txtStockAlertQty.setText("");
                            dpStockExpired.setDateFormatString("");
                            cbStockCate.setSelectedIndex(0);
                            cbStockMeasure.setSelectedIndex(0);
                            cbStockCompany.setSelectedIndex(0);
                            cbStockBranch.setSelectedIndex(0);
                            cbStockSupplier.setSelectedIndex(0);
                            txtStockName.requestFocus();
                        } else {
                            myImport.delete(myImport.getId());
                            JOptionPane.showMessageDialog(null, myImpDetail.getMessage());
                        }
                    } else {
                        myStock.delete(myStock.getId());
                        JOptionPane.showMessageDialog(null, "Failed to insert import!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to insert stock!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Expired date must not before current date!");
            }
        }
    }//GEN-LAST:event_btnStockMouseClicked

    private void btnStockListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockListMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "stocks", "read")){
            AppHelper.permissionMessage();
        } else {
            dynamicStockPane.removeAll();
            dynamicStockPane.repaint();
            dynamicStockPane.revalidate();
            dynamicStockPane.add(pnlStockList);
            dynamicStockPane.repaint();
            dynamicStockPane.revalidate();
            showStocks(getAllStocks());
        }
    }//GEN-LAST:event_btnStockListMouseClicked

    private void txtStockCateNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStockCateNameFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockCateNameFocusLost

    private void txtStockCateNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStockCateNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockCateNameActionPerformed

    private void btnDelStockCateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDelStockCateMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "stock_categories", "delete")){
            AppHelper.permissionMessage();
        } else {
            int row = tblStockCate.getSelectedRow();
            if(row < 0)
                JOptionPane.showMessageDialog(null, "Please select any category first to delete!");
            else {
                int id = (int)tblStockCate.getValueAt(row, 3);
                int res = JOptionPane.showConfirmDialog(
                    this, 
                    "Are you sure you want to delete this?", 
                    "Confirm message", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE
                );
                if(res == JOptionPane.YES_OPTION) {
                    myStockCate.delete(id);
                    showStockCate(getAllStockCate());
                }
            }
        }
    }//GEN-LAST:event_btnDelStockCateMouseClicked

    private void txtStockCateDescFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStockCateDescFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockCateDescFocusLost

    private void txtStockCateDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStockCateDescActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockCateDescActionPerformed

    private void txtSearchStockCateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchStockCateKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchStockCateKeyPressed

    private void btnAddStockCateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddStockCateMouseClicked
        // TODO add your handling code here:
        String name = txtStockCateName.getText().trim();
        String description = txtStockCateDesc.getText().trim();
        if(name.equals("") || description.equals(""))
            AppHelper.fieldRequiredMsg();
        else {            
            myStockCate.setName(name);
            myStockCate.setDescription(description);
            if(lblStockCateAdd.getText().toLowerCase().equals("add"))
                myStockCate.insert();
            else {
                myStockCate.update(stockCateId);
                lblStockCateAdd.setText("ADD");
                btnAddStockCate.setBackground(new Color(144, 202, 249));
            }
            showStockCate(getAllStockCate());
            txtStockCateName.setText("");
            txtStockCateDesc.setText("");
            txtStockCateName.requestFocus();
        }
    }//GEN-LAST:event_btnAddStockCateMouseClicked

    private void btnEditStockCateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditStockCateMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "stock_categories", "update")){
            AppHelper.permissionMessage();
        } else {
            int row = tblStockCate.getSelectedRow();
            if(row < 0)
                JOptionPane.showMessageDialog(null, "Please select any category first to edit!");
            else {
                stockCateId = (int)tblStockCate.getValueAt(row, 3);
                String sql = "SELECT * FROM `stock_categories` "
                    + "WHERE `id` = ?";            
                try {
                    ResultSet rs = AppHelper.selectQuery(sql, stockCateId);
                    if(rs.next()) {
                        txtStockCateName.setText(rs.getString("name"));
                        txtStockCateDesc.setText(rs.getString("description"));
                        lblStockCateAdd.setText("UPDATE");
                        btnAddStockCate.setBackground(new Color(19, 132, 150));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Main_Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_btnEditStockCateMouseClicked

    private void txtStockQtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStockQtyKeyPressed
        // TODO add your handling code here:
        if(AppHelper.numberOnly(evt) || AppHelper.checkDot(evt.getKeyChar(), txtStockQty.getText()))
            txtStockQty.setEditable(true);
        else
            txtStockQty.setEditable(false);
    }//GEN-LAST:event_txtStockQtyKeyPressed

    private void txtStockAlertQtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStockAlertQtyKeyPressed
        // TODO add your handling code here:
        if(AppHelper.numberOnly(evt) || AppHelper.checkDot(evt.getKeyChar(), txtStockAlertQty.getText()))
            txtStockAlertQty.setEditable(true);
        else
            txtStockAlertQty.setEditable(false);
    }//GEN-LAST:event_txtStockAlertQtyKeyPressed

    private void btnComEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnComEditMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "companies", "update")){
            AppHelper.permissionMessage();
        } else {
            int row = tblCom.getSelectedRow();
            if(row < 0)
                JOptionPane.showMessageDialog(null, "Please select any user first to edit!");
            else {
                dynamicComPnl.removeAll();
                dynamicComPnl.repaint();
                dynamicComPnl.revalidate();
                dynamicComPnl.add(pnlComAction);
                dynamicComPnl.repaint();
                dynamicComPnl.revalidate();
                lblComAction.setText("EDIT COMPANY");
                btnCom.setBackground(new Color(19, 132, 150));
                lblComBtn.setText("EDIT");
                int id = (int)tblCom.getValueAt(row, 6);
                comId = id;
                String sql = "SELECT * FROM `companies` "
                    + "WHERE `id` = ?";
                ResultSet rs = AppHelper.selectQuery(sql, id);
                try {
                    if(rs.next()) {
                        txtComName.setText(rs.getString("name"));
                        txtComEmail.setText(rs.getString("email"));
                        txtComPhone.setText(rs.getString("phone"));
                        txtComAddres.setText(rs.getString("address"));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Main_Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }   
    }//GEN-LAST:event_btnComEditMouseClicked

    private void txtSearchComKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchComKeyPressed
        // TODO add your handling code here:
        if(AppHelper.alphOnly(evt))
            txtSearchCom.setEditable(true);
        else
            txtSearchCom.setEditable(false);
        showCompanies(searchCom(txtSearchCom.getText().toLowerCase().trim()));
    }//GEN-LAST:event_txtSearchComKeyPressed

    private void btnComAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnComAddMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "companies", "create")) {
            AppHelper.permissionMessage();
        } else {
            dynamicComPnl.removeAll();
            dynamicComPnl.repaint();
            dynamicComPnl.revalidate();
            dynamicComPnl.add(pnlComAction);
            dynamicComPnl.repaint();
            dynamicComPnl.revalidate();
            lblComAction.setText("ADD COMPANY");
            btnCom.setBackground(new Color(144, 202, 249));
            lblComBtn.setText("ADD");
            txtComName.setText("");
            txtComAddres.setText("");
            txtComEmail.setText("");
            txtComPhone.setText("");
        }
    }//GEN-LAST:event_btnComAddMouseClicked

    private void btnComDelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnComDelMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "companies", "delete")){
            AppHelper.permissionMessage();
        } else {
            int row = tblCom.getSelectedRow();
            if(row < 0)
                JOptionPane.showMessageDialog(null, "Please select any companies first to delete!");
            else {
                int id = (int)tblCom.getValueAt(row, 6);
                int res = JOptionPane.showConfirmDialog(
                    this, 
                    "Are you sure you want to delete this?", 
                    "Confirm message", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE
                );
                if(res == JOptionPane.YES_OPTION) {
                    Company myCom = new Company();
                    myCom.delete(id);
                    showCompanies(getComList());
                }
            }
        }
    }//GEN-LAST:event_btnComDelMouseClicked

    private void btnComListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnComListMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "companies", "read")){
            AppHelper.permissionMessage();
        } else {
            dynamicComPnl.removeAll();
            dynamicComPnl.repaint();
            dynamicComPnl.revalidate();
            dynamicComPnl.add(pnlComList);
            dynamicComPnl.repaint();
            dynamicComPnl.revalidate();
            showCompanies(getComList());
        }
    }//GEN-LAST:event_btnComListMouseClicked

    private void btnComMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnComMouseClicked
        // TODO add your handling code here:
        String name = txtComName.getText().trim();
        String email = txtComEmail.getText().trim();
        String address = txtComAddres.getText().trim();
        String phone = txtComPhone.getText().trim();

        if(name.equals("") || address.equals("") || phone.equals("") || email.equals("")) {
            AppHelper.fieldRequiredMsg();
            txtComName.requestFocus();
        } else {
            myCompany.setName(name);
            myCompany.setEmail(email);
            myCompany.setAddress(address);
            myCompany.setPhone(phone);
            myCompany.setUserId(userId);
            if(lblComBtn.getText().toLowerCase().equals("add")) {
                if(AppHelper.isExist("companies", "name", name) == true)
                    AppHelper.existMsg();
                else {
                    myCompany.insert();
                    txtComName.setText("");
                    txtComAddres.setText("");
                    txtComEmail.setText("");
                    txtComPhone.setText("");
                    txtComName.requestFocus(); 
                }
            }
            else if(lblComBtn.getText().toLowerCase().equals("edit")) {
                if(AppHelper.isExist("companies", "name", name, comId) == true)
                    AppHelper.existMsg();
                else
                    myCompany.update(comId);
            }
            companyNames += ", '" + name.toLowerCase() + "'";   
        }
    }//GEN-LAST:event_btnComMouseClicked

    private void txtComNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtComNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtComNameActionPerformed

    private void txtComNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComNameFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtComNameFocusLost

    private void txtSearchComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchComActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchComActionPerformed

    private void txtComEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComEmailFocusLost
        // TODO add your handling code here:
        if(!txtComEmail.getText().trim().equals("") && !AppHelper.isValid(txtComEmail.getText().trim())){
            AppHelper.errorMessage("email", 0);
            txtComEmail.requestFocus();
        }
    }//GEN-LAST:event_txtComEmailFocusLost

    private void txtComEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtComEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtComEmailActionPerformed

    private void txtComPhoneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComPhoneFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtComPhoneFocusLost

    private void txtComPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtComPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtComPhoneActionPerformed

    private void txtSearchStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchStockActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchStockActionPerformed

    private void txtSearchUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchUserActionPerformed

    private void tblComMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblComMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblComMouseClicked

    private void txtComAddresFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComAddresFocusLost
        // TODO add your handling code here:
        int num = 255;
        if(AppHelper.isMatchLength("max", num, txtComAddres.getText().trim().length()) == false)
            AppHelper.errorMessage("name", num);
    }//GEN-LAST:event_txtComAddresFocusLost

    private void txtComPhoneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtComPhoneKeyPressed
        // TODO add your handling code here:
        if(AppHelper.numberOnly(evt))
            txtComPhone.setEditable(true);
        else
            txtComPhone.setEditable(false);
    }//GEN-LAST:event_txtComPhoneKeyPressed

    private void tblBranchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBranchMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblBranchMouseClicked

    private void txtSearchBranchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchBranchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchBranchActionPerformed

    private void txtSearchBranchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchBranchKeyPressed
        // TODO add your handling code here:
        if(AppHelper.alphOnly(evt))
            txtSearchBranch.setEditable(true);
        else
            txtSearchBranch.setEditable(false);
        showBranches(searchBranch(txtSearchBranch.getText().toLowerCase().trim()));
    }//GEN-LAST:event_txtSearchBranchKeyPressed

    private void txtBranchNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBranchNameFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBranchNameFocusLost

    private void txtBranchNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBranchNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBranchNameActionPerformed
    
    private void btnBranchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBranchMouseClicked
        // TODO add your handling code here:
        String name = txtBranchName.getText().trim();
        String email = txtBranchEmail.getText().trim();
        String address = txtBranchAddr.getText().trim();
        String phone = txtBranchPhone.getText().trim();
        String comName = String.valueOf(cbBranchCom.getSelectedItem());

        if(name.equals("") || address.equals("") || phone.equals("") || email.equals("")) {
            AppHelper.fieldRequiredMsg();
            txtComName.requestFocus();
        } else {
            int companyId = AppHelper.getId(comName, "id", "companies", "name");
            myBranch.setName(name);
            myBranch.setEmail(email);
            myBranch.setAddress(address);
            myBranch.setPhone(phone);
            myBranch.setComId(companyId);
            myBranch.setUserId(userId);
            if(lblBranchBtn.getText().toLowerCase().equals("add")) {
                if(AppHelper.isExist("branches", "name", name) == true)
                    AppHelper.existMsg();
                else {
                    myBranch.insert();
                    txtBranchName.setText("");
                    txtBranchAddr.setText("");
                    txtBranchEmail.setText("");
                    txtBranchPhone.setText("");
                    txtBranchName.requestFocus();
                }               
            }
            else if(lblBranchBtn.getText().toLowerCase().equals("edit")) {
                if(AppHelper.isExist("branches", "name", name, branchId) == true)
                    AppHelper.existMsg();
                else
                    myBranch.update(branchId);
            }                
        }
    }//GEN-LAST:event_btnBranchMouseClicked

    private void txtBranchPhoneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBranchPhoneFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBranchPhoneFocusLost

    private void txtBranchPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBranchPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBranchPhoneActionPerformed

    private void txtBranchEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBranchEmailFocusLost
        // TODO add your handling code here:
        if(!txtBranchEmail.getText().trim().equals("") && !AppHelper.isValid(txtBranchEmail.getText().trim())){
            AppHelper.errorMessage("email", 0);
            txtBranchEmail.requestFocus();
        }
    }//GEN-LAST:event_txtBranchEmailFocusLost

    private void txtBranchEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBranchEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBranchEmailActionPerformed

    private void txtBranchEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBranchEmailKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBranchEmailKeyPressed

    private void txtBranchAddrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBranchAddrFocusLost
        // TODO add your handling code here:
        int num = 255;
        if(AppHelper.isMatchLength("max", num, txtBranchAddr.getText().trim().length()) == false)
            AppHelper.errorMessage("name", num);
    }//GEN-LAST:event_txtBranchAddrFocusLost

    private void btnBranchAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBranchAddMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "branches", "create")) {
            AppHelper.permissionMessage();
        } else {
            dynamicBranchPnl.removeAll();
            dynamicBranchPnl.repaint();
            dynamicBranchPnl.revalidate();
            dynamicBranchPnl.add(pnlBranchAction);
            dynamicBranchPnl.repaint();
            dynamicBranchPnl.revalidate();
            lblBranchAction.setText("ADD BRANCH");
            btnBranch.setBackground(new Color(144, 202, 249));
            lblBranchBtn.setText("ADD");
            txtBranchName.setText("");
            txtBranchAddr.setText("");
            txtBranchPhone.setText("");
            txtBranchEmail.setText("");
            cbBranchCom.removeAllItems();
            AppHelper.getCombos("name", "companies", "user_id", userId)
                .forEach((r) -> cbBranchCom.addItem(r));
            cbBranchCom.setSelectedIndex(0);
        }
    }//GEN-LAST:event_btnBranchAddMouseClicked

    private void btnBranchEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBranchEditMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "branches", "update")){
            AppHelper.permissionMessage();
        } else {
            int row = tblBranch.getSelectedRow();
            if(row < 0)
                JOptionPane.showMessageDialog(null, "Please select any branch first to edit!");
            else {
                dynamicBranchPnl.removeAll();
                dynamicBranchPnl.repaint();
                dynamicBranchPnl.revalidate();
                dynamicBranchPnl.add(pnlBranchAction);
                dynamicBranchPnl.repaint();
                dynamicBranchPnl.revalidate();
                lblBranchAction.setText("EDIT BRANCH");
                btnBranch.setBackground(new Color(19, 132, 150));
                lblBranchBtn.setText("EDIT");
                cbBranchCom.removeAllItems();
                AppHelper.getCombos("name", "companies", "user_id", userId)
                    .forEach((r) -> cbBranchCom.addItem(r));
                int id = (int)tblBranch.getValueAt(row, 6);
                branchId = id;
                String sql = "SELECT `branches`.*, "
                    + "`companies`.`name` AS company_name "
                    + "FROM `branches` "
                    + "INNER JOIN `companies` ON `branches`.`company_id` = `companies`.`id`"
                    + "WHERE `branches`.`id` = ?";
                ResultSet rs = AppHelper.selectQuery(sql, id);
                try {
                    if(rs.next()) {
                        txtBranchName.setText(rs.getString("name"));
                        txtBranchEmail.setText(rs.getString("email"));
                        txtBranchPhone.setText(rs.getString("phone"));
                        txtBranchAddr.setText(rs.getString("address"));
                        cbBranchCom.setSelectedItem(rs.getString("company_name"));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Main_Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }  
    }//GEN-LAST:event_btnBranchEditMouseClicked

    private void btnBranchDelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBranchDelMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "branches", "delete")){
            AppHelper.permissionMessage();
        } else {
            int row = tblBranch.getSelectedRow();
            if(row < 0)
                JOptionPane.showMessageDialog(null, "Please select any branch first to delete!");
            else {
                int id = (int)tblBranch.getValueAt(row, 6);
                int res = JOptionPane.showConfirmDialog(
                    this, 
                    "Are you sure you want to delete this?", 
                    "Confirm message", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE
                );
                if(res == JOptionPane.YES_OPTION) {
                    Branch myB = new Branch();
                    myB.delete(id);
                    showBranches(getBranchList());
                }
            }
        }
    }//GEN-LAST:event_btnBranchDelMouseClicked

    private void btnBranchListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBranchListMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "branches", "read")) {
            AppHelper.permissionMessage();
        } else {
            dynamicBranchPnl.removeAll();
            dynamicBranchPnl.repaint();
            dynamicBranchPnl.revalidate();
            dynamicBranchPnl.add(pnlBranchList);
            dynamicBranchPnl.repaint();
            dynamicBranchPnl.revalidate();
            showBranches(getBranchList());
        }
    }//GEN-LAST:event_btnBranchListMouseClicked

    private void txtBranchPhoneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBranchPhoneKeyPressed
        // TODO add your handling code here:
        if(AppHelper.numberOnly(evt))
            txtBranchPhone.setEditable(true);
        else
            txtBranchPhone.setEditable(false);
    }//GEN-LAST:event_txtBranchPhoneKeyPressed

    private void tblSupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSupMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblSupMouseClicked

    private void txtSearchSupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchSupActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchSupActionPerformed

    private void txtSearchSupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchSupKeyPressed
        // TODO add your handling code here:
        if(AppHelper.alphOnly(evt))
            txtSearchSup.setEditable(true);
        else
            txtSearchSup.setEditable(false);
        showSup(searchSup(txtSearchSup.getText().toLowerCase().trim()));
    }//GEN-LAST:event_txtSearchSupKeyPressed

    private void txtSupNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSupNameFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSupNameFocusLost

    private void txtSupNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSupNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSupNameActionPerformed

    private void btnSupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSupMouseClicked
        // TODO add your handling code here:
        String name = txtSupName.getText().trim();
        String email = txtSupEmail.getText().trim();
        String address = txtSupAddr.getText().trim();
        String phone = txtSupPhone.getText().trim();
        String supBranch = String.valueOf(cbSupBranch.getSelectedItem());

        if(name.equals("") || address.equals("") || phone.equals("") || email.equals("")) {
            AppHelper.fieldRequiredMsg();
            txtComName.requestFocus();
        } else {
            int bId = AppHelper.getId(supBranch, "id", "branches", "name");
            int companyId = AppHelper.getId(supBranch, "company_id", "branches", "name");
            mySupplier.setName(name);
            mySupplier.setEmail(email);
            mySupplier.setAddress(address);
            mySupplier.setPhone(phone);
            mySupplier.setComId(companyId);
            mySupplier.setBranchId(bId);
            mySupplier.setUserId(userId);
            if(lblSupBtn.getText().toLowerCase().equals("add")) {
                if(AppHelper.isExist("suppliers", "name", name) == true)
                    AppHelper.existMsg();
                else {
                    mySupplier.insert();
                    txtSupName.setText("");
                    txtSupAddr.setText("");
                    txtSupEmail.setText("");
                    txtSupPhone.setText("");
                    txtSupName.requestFocus();
                    cbSupBranch.setSelectedIndex(0);
                }               
            }
            else if(lblSupBtn.getText().toLowerCase().equals("edit")) {
                if(AppHelper.isExist("suppliers", "name", name, supId) == true)
                    AppHelper.existMsg();
                else
                    mySupplier.update(supId);
            }                
        }
    }//GEN-LAST:event_btnSupMouseClicked

    private void txtSupEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSupEmailFocusLost
        // TODO add your handling code here:
        if(!txtSupEmail.getText().trim().equals("") && !AppHelper.isValid(txtSupEmail.getText().trim())){
            AppHelper.errorMessage("email", 0);
            txtSupEmail.requestFocus();
        }
    }//GEN-LAST:event_txtSupEmailFocusLost

    private void txtSupEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSupEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSupEmailActionPerformed

    private void txtSupPhoneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSupPhoneFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSupPhoneFocusLost

    private void txtSupPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSupPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSupPhoneActionPerformed

    private void txtSupPhoneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSupPhoneKeyPressed
        // TODO add your handling code here:
        if(AppHelper.numberOnly(evt))
            txtSupPhone.setEditable(true);
        else
            txtSupPhone.setEditable(false);
    }//GEN-LAST:event_txtSupPhoneKeyPressed

    private void txtSupAddrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSupAddrFocusLost
        // TODO add your handling code here:
        int num = 255;
        if(AppHelper.isMatchLength("max", num, txtSupAddr.getText().trim().length()) == false)
            AppHelper.errorMessage("name", num);
    }//GEN-LAST:event_txtSupAddrFocusLost

    private void btnSupAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSupAddMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "suppliers", "create")) {
            AppHelper.permissionMessage();
        } else {
            dynamicSupPnl.removeAll();
            dynamicSupPnl.repaint();
            dynamicSupPnl.revalidate();
            dynamicSupPnl.add(pnlSupAction);
            dynamicSupPnl.repaint();
            dynamicSupPnl.revalidate();
            lblSupAction.setText("ADD SUPPLIER");
            btnSup.setBackground(new Color(144, 202, 249));
            lblSupBtn.setText("ADD");
            txtSupName.setText("");
            txtSupAddr.setText("");
            txtSupEmail.setText("");
            txtSupPhone.setText("");            
            cbSupBranch.removeAllItems();
            AppHelper.getCombos("name", "branches", "user_id", userId)
                .forEach((r) -> cbSupBranch.addItem(r));
            cbSupBranch.setSelectedIndex(0);
        }
    }//GEN-LAST:event_btnSupAddMouseClicked

    private void btnSupEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSupEditMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "suppliers", "update")){
            AppHelper.permissionMessage();
        } else {
            int row = tblSup.getSelectedRow();
            if(row < 0)
                JOptionPane.showMessageDialog(null, "Please select any branch first to edit!");
            else {
                dynamicSupPnl.removeAll();
                dynamicSupPnl.repaint();
                dynamicSupPnl.revalidate();
                dynamicSupPnl.add(pnlSupAction);
                dynamicSupPnl.repaint();
                dynamicSupPnl.revalidate();
                lblSupAction.setText("EDIT SUPPLIER");
                btnSup.setBackground(new Color(19, 132, 150));
                lblSupBtn.setText("EDIT");
                cbSupBranch.removeAllItems();
                AppHelper.getCombos("name", "branches", "user_id", userId)
                    .forEach((r) -> cbSupBranch.addItem(r));
                cbSupBranch.setSelectedIndex(0);
                int id = (int)tblSup.getValueAt(row, 7);
                supId = id;
                String sql = "SELECT `suppliers`.*, "
                    + "`branches`.`name` AS branch_name "
                    + "FROM `suppliers` "
                    + "INNER JOIN `branches` "
                    + "ON `suppliers`.`branch_id` = `branches`.`id` "
                    + "WHERE `suppliers`.`id` = ?";
                ResultSet rs = AppHelper.selectQuery(sql, id);
                try {
                    if(rs.next()) {
                        txtSupName.setText(rs.getString("name"));
                        txtSupEmail.setText(rs.getString("email"));
                        txtSupPhone.setText(rs.getString("phone"));
                        txtSupAddr.setText(rs.getString("address"));
                        cbSupBranch.setSelectedItem(rs.getString("branch_name"));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Main_Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }  
    }//GEN-LAST:event_btnSupEditMouseClicked

    private void btnSupDelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSupDelMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "suppliers", "delete")){
            AppHelper.permissionMessage();
        } else {
            int row = tblSup.getSelectedRow();
            if(row < 0)
                JOptionPane.showMessageDialog(null, "Please select any supplier first to delete!");
            else {
                int id = (int)tblSup.getValueAt(row, 7);
                int res = JOptionPane.showConfirmDialog(
                    this, 
                    "Are you sure you want to delete this?", 
                    "Confirm message", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE
                );
                if(res == JOptionPane.YES_OPTION) {
                    Supplier mySup = new Supplier();
                    mySup.delete(id);
                    showSup(getSupList());
                }
            }
        }
    }//GEN-LAST:event_btnSupDelMouseClicked

    private void btnSupListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSupListMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "suppliers", "read")) {
            AppHelper.permissionMessage();
        } else {
            dynamicSupPnl.removeAll();
            dynamicSupPnl.repaint();
            dynamicSupPnl.revalidate();
            dynamicSupPnl.add(pnlSupList);
            dynamicSupPnl.repaint();
            dynamicSupPnl.revalidate();
            showSup(getSupList());
        }
    }//GEN-LAST:event_btnSupListMouseClicked

    private void txtStockPriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStockPriceKeyPressed
        // TODO add your handling code here:
        if(AppHelper.numberOnly(evt) || AppHelper.checkDot(evt.getKeyChar(), txtStockPrice.getText()))
            txtStockPrice.setEditable(true);
        else
            txtStockPrice.setEditable(false);
    }//GEN-LAST:event_txtStockPriceKeyPressed

    private void txtProNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProNameFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProNameFocusLost

    private void txtProNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProNameActionPerformed
    
    private void btnProMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProMouseClicked
        // TODO add your handling code here:
        if(txtProName.getText().trim().equals("")) {
            AppHelper.fieldRequiredMsg();
            txtProName.requestFocus();
        } else {
            String name = txtProName.getText().trim();
            if(AppHelper.isExist("products", "name", name)) {
                AppHelper.existMsg();
                txtProName.setText("");
                txtProDesc.setText("");
                cbProCate.setSelectedIndex(0);
                cbProBranch.setSelectedIndex(0);
                txtProName.requestFocus();
                showProduct(getAllProducts());
            } else {                
                String desc = txtProDesc.getText().trim();                   

                int pcId = AppHelper.getId(
                    String.valueOf(cbProCate.getSelectedItem()), 
                    "id", 
                    "product_categories", 
                    "name"
                );
                int bId = AppHelper.getId(
                    String.valueOf(cbProBranch.getSelectedItem()), 
                    "id", 
                    "branches", 
                    "name"
                );
                int cId = AppHelper.getId(
                    String.valueOf(cbProBranch.getSelectedItem()), 
                    "company_id", 
                    "branches", 
                    "name"
                );
                myProduct.setName(name);            
                myProduct.setDescription(desc);
                myProduct.setProCateId(pcId);
                myProduct.setComId(cId);
                myProduct.setBranchId(bId);
                myProduct.setUserId(userId);
                myProduct.insert();

                txtProName.setText("");
                txtProDesc.setText("");
                cbProCate.setSelectedIndex(0);
                cbProBranch.setSelectedIndex(0);
                txtProName.requestFocus();
                showProduct(getAllProducts());
            }
        }
    }//GEN-LAST:event_btnProMouseClicked

    private void txtSearchProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchProActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchProActionPerformed

    private void txtSearchProKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchProKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchProKeyPressed

    private void txtProCateNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProCateNameFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProCateNameFocusLost

    private void txtProCateNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProCateNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProCateNameActionPerformed

    private void btnDelProCateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDelProCateMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "product_categories", "delete")){
            AppHelper.permissionMessage();
        } else {
            int row = tblProCate.getSelectedRow();
            if(row < 0)
                JOptionPane.showMessageDialog(null, "Please select any category first to delete!");
            else {
                int id = (int)tblProCate.getValueAt(row, 3);
                int res = JOptionPane.showConfirmDialog(
                    this, 
                    "Are you sure you want to delete this?", 
                    "Confirm message", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE
                );
                if(res == JOptionPane.YES_OPTION) {
                    myProCate.delete(id);
                    showProCate(getAllProCate());
                }
            }
        }
    }//GEN-LAST:event_btnDelProCateMouseClicked

    private void txtProCateDescFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProCateDescFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProCateDescFocusLost

    private void txtProCateDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProCateDescActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProCateDescActionPerformed

    private void txtSearchProCateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchProCateKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchProCateKeyPressed
    
    private void btnAddProCateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddProCateMouseClicked
        // TODO add your handling code here:
        String name = txtProCateName.getText().trim();
        String description = txtProCateDesc.getText().trim();
        if(name.equals("") || description.equals(""))
            AppHelper.fieldRequiredMsg();
        else {            
            myProCate.setName(name);
            myProCate.setDescription(description);
            if(lblProCateAdd.getText().toLowerCase().equals("add"))
                myProCate.insert();
            else {
                myProCate.update(proCateId);
                lblProCateAdd.setText("ADD");
                btnAddProCate.setBackground(new Color(144, 202, 249));
            }
            showProCate(getAllProCate());
            txtProCateName.setText("");
            txtProCateDesc.setText("");
            txtProCateName.requestFocus();
        }
    }//GEN-LAST:event_btnAddProCateMouseClicked

    private void btnEditProCateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditProCateMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "product_categories", "update")){
            AppHelper.permissionMessage();
        } else {
            int row = tblProCate.getSelectedRow();
            if(row < 0)
                JOptionPane.showMessageDialog(null, "Please select any category first to edit!");
            else {
                proCateId = (int)tblProCate.getValueAt(row, 3);
                String sql = "SELECT * FROM `product_categories` "
                    + "WHERE `id` = ?";            
                try {
                    ResultSet rs = AppHelper.selectQuery(sql, proCateId);
                    if(rs.next()) {
                        txtProCateName.setText(rs.getString("name"));
                        txtProCateDesc.setText(rs.getString("description"));
                        lblProCateAdd.setText("UPDATE");
                        btnAddProCate.setBackground(new Color(19, 132, 150));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Main_Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_btnEditProCateMouseClicked

    private void btnProductVarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductVarMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "products", "create"))
            AppHelper.permissionMessage();
        else {
            dynamicProPane.removeAll();
            dynamicProPane.repaint();
            dynamicProPane.revalidate();
            dynamicProPane.add(pnlProVar);
            dynamicProPane.repaint();
            dynamicProPane.revalidate();
            showProductVar(getAllProductVars());
            JTableHeader header = tblProVar.getTableHeader();
            header.setFont(new Font("Segoe UI", Font.BOLD, 26));
            header.setOpaque(false);
            header.setForeground(Color.WHITE);
            header.setBackground(Color.black);
            lblProVarAction.setText("ADD PRODUCT VARIANT");
            btnProVar.setBackground(new Color(144, 202, 249));
            lblProVarBtn.setText("ADD");
            txtProVarPrice.setText("");
            txtProVarSellingPrice.setText("");
            cbProVarSize.setSelectedIndex(0);
            cbProVarProduct.removeAllItems();
            AppHelper.getCombos("name", "products", "user_id", userId)
                .forEach((r) -> cbProVarProduct.addItem(r));
            cbProVarProduct.setSelectedIndex(0);           

            ImageIcon img = new ImageIcon(System.getProperty("user.dir") + "\\src\\coffee_shop_java\\products\\images.png");
            Image newImg = img.getImage().getScaledInstance(proImg.getWidth(), proImg.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon myImg = new ImageIcon(newImg);
            proImg.setIcon(myImg);                
        }
    }//GEN-LAST:event_btnProductVarMouseClicked

    private void btnProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "products", "create"))
            AppHelper.permissionMessage();
        else {
            dynamicProPane.removeAll();
            dynamicProPane.repaint();
            dynamicProPane.revalidate();
            dynamicProPane.add(pnlPro);
            dynamicProPane.repaint();
            dynamicProPane.revalidate();            
            showProduct(getAllProducts());
            JTableHeader header = tblProduct.getTableHeader();
            header.setFont(new Font("Segoe UI", Font.BOLD, 26));
            header.setOpaque(false);
            header.setForeground(Color.WHITE);
            header.setBackground(Color.black);
            lblProAction.setText("ADD PRODUCT");
            btnPro.setBackground(new Color(144, 202, 249));
            lblProBtn.setText("ADD");
        }
    }//GEN-LAST:event_btnProductMouseClicked

    private void btnProductCateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductCateMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "product_categories", "read"))
            AppHelper.permissionMessage();
        else {
            dynamicProPane.removeAll();
            dynamicProPane.repaint();
            dynamicProPane.revalidate();
            dynamicProPane.add(pnlProCate);
            dynamicProPane.repaint();
            dynamicProPane.revalidate();

            JTableHeader header = tblProCate.getTableHeader();
            header.setFont(new Font("Segoe UI", Font.BOLD, 26));
            header.setOpaque(false);
            header.setForeground(Color.WHITE);
            header.setBackground(Color.black);
            showProCate(getAllProCate());
            txtProCateName.requestFocus();
        }
    }//GEN-LAST:event_btnProductCateMouseClicked

    private void btnProListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProListMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "products", "read")){
            AppHelper.permissionMessage();
        } else {
            dynamicProPane.removeAll();
            dynamicProPane.repaint();
            dynamicProPane.revalidate();
            dynamicProPane.add(pnlProList);
            dynamicProPane.repaint();
            dynamicProPane.revalidate();
            showProductVar(getAllProductVars());
        }
    }//GEN-LAST:event_btnProListMouseClicked

    private void txtProDescFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProDescFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProDescFocusLost

    private void txtProDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProDescActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProDescActionPerformed

    private void btnStockProMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockProMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "products", "update"))
            AppHelper.permissionMessage();
        else {
            int row = tblPro.getSelectedRow();
            if(row < 0)
                JOptionPane.showMessageDialog(null, "Please select any product first!");
            else {
                int id = (int)tblPro.getValueAt(row, 5);
                new StockProductForm(id, roleId, userId).setVisible(true);
            }
        }
    }//GEN-LAST:event_btnStockProMouseClicked

    private void btnProVarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProVarMouseClicked
        // TODO add your handling code here:
        double price = Double.valueOf(txtProVarPrice.getText().trim());
        double sellingPrice = Double.valueOf(txtProVarSellingPrice.getText().trim());
        String size = String.valueOf(cbProVarSize.getSelectedItem());
        String product = String.valueOf(cbProVarProduct.getSelectedItem());
        int productId = AppHelper.getId(product, "id", "products", "name");
        
        if(
            txtProVarPrice.getText().trim().equals("") ||
            txtProVarSellingPrice.getText().trim().equals("")
        )
        {
            AppHelper.fieldRequiredMsg();
        }
        else
        {
            System.out.println(productId);
            myProVar.setSize(size.toLowerCase());
            myProVar.setPrice(price);
            myProVar.setSellingPrice(sellingPrice);
            myProVar.setProId(productId);
            myProVar.insert();
            
            txtProVarPrice.setText("");
            txtProVarSellingPrice.setText("");
            cbProVarSize.setSelectedIndex(0);
            cbProVarProduct.setSelectedIndex(0);
            ImageIcon img = new ImageIcon(System.getProperty("user.dir") + "\\src\\coffee_shop_java\\products\\images.png");
            Image newImg = img.getImage().getScaledInstance(proImg.getWidth(), proImg.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon myImg = new ImageIcon(newImg);
            proImg.setIcon(myImg); 
            
            new StockProductForm(myProVar.getId(), roleId, userId).setVisible(true);
        }
    }//GEN-LAST:event_btnProVarMouseClicked

    private void txtProVarPriceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProVarPriceFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProVarPriceFocusLost

    private void txtProVarPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProVarPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProVarPriceActionPerformed

    private void txtProVarPriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProVarPriceKeyPressed
        // TODO add your handling code here:
        if(AppHelper.numberOnly(evt) || AppHelper.checkDot(evt.getKeyChar(), txtProVarPrice.getText()))
            txtProVarPrice.setEditable(true);
        else
            txtProVarPrice.setEditable(false);
    }//GEN-LAST:event_txtProVarPriceKeyPressed

    private void txtProVarSellingPriceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProVarSellingPriceFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProVarSellingPriceFocusLost

    private void txtProVarSellingPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProVarSellingPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProVarSellingPriceActionPerformed

    private void txtProVarSellingPriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProVarSellingPriceKeyPressed
        // TODO add your handling code here:
        if(AppHelper.numberOnly(evt) || AppHelper.checkDot(evt.getKeyChar(), txtProVarSellingPrice.getText()))
            txtProVarSellingPrice.setEditable(true);
        else
            txtProVarSellingPrice.setEditable(false);
    }//GEN-LAST:event_txtProVarSellingPriceKeyPressed

    private void btnProVarImgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProVarImgMouseClicked
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File filePath = chooser.getSelectedFile();
        String fileName = chooser.getSelectedFile().getName();
        String copyPath = System.getProperty("user.dir") + "\\src\\coffee_shop_java\\products";
        File copyFile = new File(copyPath);

        try {
            FileUtils.copyFileToDirectory(filePath, copyFile);
            JOptionPane.showMessageDialog(null, "Image uploaded!");
            ImageIcon img = new ImageIcon(copyPath + "\\" + fileName);
            Image newImg = img.getImage().getScaledInstance(proImg.getWidth(), proImg.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon myImg = new ImageIcon(newImg);
            proImg.setIcon(myImg);
            myProVar.setImage("\\src\\coffee_shop_java\\products\\" + fileName);
        } catch (IOException ex) {
            Logger.getLogger(Main_Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnProVarImgMouseClicked

    private void btnProVarEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProVarEditMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnProVarEditMouseClicked

    private void btnProVarDelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProVarDelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnProVarDelMouseClicked

    private void txtProVarSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProVarSearchKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProVarSearchKeyPressed

    private void txtProVarSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProVarSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProVarSearchActionPerformed

    private void txtProVarSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProVarSearchFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProVarSearchFocusLost

    private void txtProSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProSearchFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProSearchFocusLost

    private void txtProSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProSearchActionPerformed

    private void txtProSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProSearchKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProSearchKeyPressed

    private void btnProDelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProDelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnProDelMouseClicked

    private void btnProEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProEditMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnProEditMouseClicked

    private void btnProductStockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductStockMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "products", "update"))
            AppHelper.permissionMessage();
        else {
            int row = tblProVar.getSelectedRow();
            if(row < 0)
                JOptionPane.showMessageDialog(null, "Please select any product variant first!");
            else {
                int id = (int)tblProVar.getValueAt(row, 4);
                new StockProductForm(id, roleId, userId).setVisible(true);
            }
        }
    }//GEN-LAST:event_btnProductStockMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main_Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main_Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main_Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main_Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Main_Menu().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel LineEmail;
    private javax.swing.JPanel LineEmail1;
    private javax.swing.JPanel LineEmail2;
    private javax.swing.JPanel LineName;
    private javax.swing.JPanel LineName1;
    private javax.swing.JPanel LineName2;
    private javax.swing.JPanel LineName4;
    private javax.swing.JPanel LinePhone;
    private javax.swing.JPanel LinePhone1;
    private javax.swing.JPanel LinePhone2;
    private javax.swing.JPanel LineTxtAddress;
    private javax.swing.JPanel LineTxtAddress1;
    private javax.swing.JPanel LineTxtAddress2;
    private javax.swing.JPanel background;
    private javax.swing.JPanel braActive;
    private javax.swing.JPanel braBlink;
    private javax.swing.JLayeredPane braLayer;
    private javax.swing.JLabel branch;
    private javax.swing.JLabel branchIcon;
    private javax.swing.JPanel branchLabel;
    private javax.swing.JPanel branchPanel;
    private javax.swing.JPanel btnAddProCate;
    private javax.swing.JPanel btnAddStockCate;
    private javax.swing.JPanel btnAddUser;
    private javax.swing.JPanel btnBranch;
    private javax.swing.JPanel btnBranchAdd;
    private javax.swing.JPanel btnBranchDel;
    private javax.swing.JPanel btnBranchEdit;
    private javax.swing.JPanel btnBranchList;
    private javax.swing.JPanel btnChangePass;
    private javax.swing.JPanel btnCom;
    private javax.swing.JPanel btnComAdd;
    private javax.swing.JPanel btnComDel;
    private javax.swing.JPanel btnComEdit;
    private javax.swing.JPanel btnComList;
    private javax.swing.JPanel btnDelProCate;
    private javax.swing.JPanel btnDelStockCate;
    private javax.swing.JPanel btnDelUser;
    private javax.swing.JPanel btnEditProCate;
    private javax.swing.JPanel btnEditStockCate;
    private javax.swing.JPanel btnEditUser;
    private javax.swing.JPanel btnLogout;
    private javax.swing.JPanel btnPermission;
    private javax.swing.JPanel btnPro;
    private javax.swing.JPanel btnProDel;
    private javax.swing.JPanel btnProEdit;
    private javax.swing.JPanel btnProList;
    private javax.swing.JPanel btnProVar;
    private javax.swing.JPanel btnProVarDel;
    private javax.swing.JPanel btnProVarEdit;
    private javax.swing.JPanel btnProVarImg;
    private javax.swing.JPanel btnProduct;
    private javax.swing.JPanel btnProductCate;
    private javax.swing.JPanel btnProductStock;
    private javax.swing.JPanel btnProductVar;
    private javax.swing.JPanel btnRole;
    private javax.swing.JLabel btnRoleIcon;
    private javax.swing.JLabel btnRoleIcon1;
    private javax.swing.JLabel btnRoleIcon2;
    private javax.swing.JLabel btnRoleIcon4;
    private javax.swing.JLabel btnRoleLbl;
    private javax.swing.JLabel btnRoleLbl1;
    private javax.swing.JLabel btnRoleLbl3;
    private javax.swing.JPanel btnStock;
    private javax.swing.JPanel btnStockCate;
    private javax.swing.JPanel btnStockDel;
    private javax.swing.JPanel btnStockEdit;
    private javax.swing.JPanel btnStockImport;
    private javax.swing.JPanel btnStockList;
    private javax.swing.JPanel btnStockPro;
    private javax.swing.JPanel btnSup;
    private javax.swing.JPanel btnSupAdd;
    private javax.swing.JPanel btnSupDel;
    private javax.swing.JPanel btnSupEdit;
    private javax.swing.JPanel btnSupList;
    private javax.swing.JLabel btnUserIcon;
    private javax.swing.JLabel btnUserIcon1;
    private javax.swing.JLabel btnUserIcon11;
    private javax.swing.JLabel btnUserIcon12;
    private javax.swing.JLabel btnUserIcon13;
    private javax.swing.JLabel btnUserIcon2;
    private javax.swing.JLabel btnUserIcon3;
    private javax.swing.JLabel btnUserIcon4;
    private javax.swing.JLabel btnUserIcon5;
    private javax.swing.JLabel btnUserIcon6;
    private javax.swing.JLabel btnUserIcon7;
    private javax.swing.JLabel btnUserIcon8;
    private javax.swing.JLabel btnUserIcon9;
    private javax.swing.JLabel btnUserIconComAdd;
    private javax.swing.JLabel btnUserIconComAdd1;
    private javax.swing.JLabel btnUserIconComAdd2;
    private javax.swing.JLabel btnUserIconComEdit;
    private javax.swing.JLabel btnUserIconComEdit1;
    private javax.swing.JLabel btnUserIconComEdit2;
    private javax.swing.JLabel btnUserIconComList;
    private javax.swing.JLabel btnUserIconComList1;
    private javax.swing.JLabel btnUserIconComList2;
    private javax.swing.JLabel btnUserIconDel;
    private javax.swing.JLabel btnUserIconDel1;
    private javax.swing.JLabel btnUserIconDel2;
    private javax.swing.JLabel btnUserLbl;
    private javax.swing.JLabel btnUserLbl1;
    private javax.swing.JLabel btnUserLbl11;
    private javax.swing.JLabel btnUserLbl12;
    private javax.swing.JLabel btnUserLbl13;
    private javax.swing.JLabel btnUserLbl2;
    private javax.swing.JLabel btnUserLbl3;
    private javax.swing.JLabel btnUserLbl4;
    private javax.swing.JLabel btnUserLbl5;
    private javax.swing.JLabel btnUserLbl6;
    private javax.swing.JLabel btnUserLbl7;
    private javax.swing.JLabel btnUserLbl8;
    private javax.swing.JLabel btnUserLbl9;
    private javax.swing.JLabel btnUserLblComAdd;
    private javax.swing.JLabel btnUserLblComAdd1;
    private javax.swing.JLabel btnUserLblComAdd2;
    private javax.swing.JLabel btnUserLblComEdit;
    private javax.swing.JLabel btnUserLblComEdit1;
    private javax.swing.JLabel btnUserLblComEdit2;
    private javax.swing.JLabel btnUserLblComList;
    private javax.swing.JLabel btnUserLblComList1;
    private javax.swing.JLabel btnUserLblComList2;
    private javax.swing.JLabel btnUserLblDel;
    private javax.swing.JLabel btnUserLblDel1;
    private javax.swing.JLabel btnUserLblDel2;
    private javax.swing.JPanel btnUserRefresh;
    private javax.swing.JComboBox<String> cbBranchCom;
    private javax.swing.JComboBox<String> cbProBranch;
    private javax.swing.JComboBox<String> cbProCate;
    private javax.swing.JComboBox<String> cbProVarProduct;
    private javax.swing.JComboBox<String> cbProVarSize;
    private javax.swing.JComboBox<String> cbStockBranch;
    private javax.swing.JComboBox<String> cbStockCate;
    private javax.swing.JComboBox<String> cbStockCompany;
    private javax.swing.JComboBox<String> cbStockMeasure;
    private javax.swing.JComboBox<String> cbStockSupplier;
    private javax.swing.JComboBox<String> cbSupBranch;
    private javax.swing.JPanel comActive;
    private javax.swing.JPanel comBlink;
    private javax.swing.JLabel comIcon;
    private javax.swing.JPanel comLabel;
    private javax.swing.JLayeredPane comLayer;
    private javax.swing.JPanel comPanel;
    private javax.swing.JLabel company;
    private javax.swing.JLabel company1;
    private javax.swing.JLabel company2;
    private javax.swing.JPanel dashActive;
    private javax.swing.JPanel dashBlink;
    private javax.swing.JLabel dashIcon;
    private javax.swing.JLayeredPane dashLayp;
    private javax.swing.JLabel dashLbl;
    private javax.swing.JPanel dashPanel;
    private javax.swing.JPanel dashPnl;
    private com.toedter.calendar.JDateChooser dpStockExpired;
    private javax.swing.JLayeredPane dynamicBranchPnl;
    private javax.swing.JLayeredPane dynamicComPnl;
    private javax.swing.JLabel dynamicLabel;
    private javax.swing.JLayeredPane dynamicPanel;
    private javax.swing.JLayeredPane dynamicProPane;
    private javax.swing.JLayeredPane dynamicStockPane;
    private javax.swing.JLayeredPane dynamicSupPnl;
    private javax.swing.JLabel exitIcon;
    private javax.swing.JDesktopPane jDesktopPane2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lbEmail;
    private javax.swing.JLabel lbEmail1;
    private javax.swing.JLabel lbEmail2;
    private javax.swing.JLabel lblAdd4;
    private javax.swing.JLabel lblAdd5;
    private javax.swing.JLabel lblAdd7;
    private javax.swing.JLabel lblAdd8;
    private javax.swing.JLabel lblAddresss;
    private javax.swing.JLabel lblAddresss1;
    private javax.swing.JLabel lblAddresss2;
    private javax.swing.JLabel lblBranchAction;
    private javax.swing.JLabel lblBranchBtn;
    private javax.swing.JLabel lblComAction;
    private javax.swing.JLabel lblComBtn;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblName1;
    private javax.swing.JLabel lblName2;
    private javax.swing.JLabel lblName3;
    private javax.swing.JLabel lblName5;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblPhone1;
    private javax.swing.JLabel lblPhone2;
    private javax.swing.JLabel lblProAction;
    private javax.swing.JLabel lblProBtn;
    private javax.swing.JLabel lblProCateAdd;
    private javax.swing.JLabel lblProVarAction;
    private javax.swing.JLabel lblProVarBtn;
    private javax.swing.JLabel lblProVarBtn1;
    private javax.swing.JLabel lblProVarBtn2;
    private javax.swing.JLabel lblProVarBtn3;
    private javax.swing.JLabel lblProVarBtn4;
    private javax.swing.JLabel lblProVarBtn5;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblSearch1;
    private javax.swing.JLabel lblSearch2;
    private javax.swing.JLabel lblStockAction;
    private javax.swing.JLabel lblStockBtn;
    private javax.swing.JLabel lblStockCateAdd;
    private javax.swing.JLabel lblSupAction;
    private javax.swing.JLabel lblSupBtn;
    private javax.swing.JPanel lineSearch;
    private javax.swing.JPanel lineSearch1;
    private javax.swing.JPanel lineSearch2;
    private javax.swing.JPanel pnlBranchAction;
    private javax.swing.JPanel pnlBranchList;
    private javax.swing.JPanel pnlComAction;
    private javax.swing.JPanel pnlComList;
    private javax.swing.JPanel pnlPro;
    private javax.swing.JPanel pnlProCate;
    private javax.swing.JPanel pnlProList;
    private javax.swing.JPanel pnlProVar;
    private javax.swing.JPanel pnlStockCate;
    private javax.swing.JPanel pnlStockImport;
    private javax.swing.JPanel pnlStockList;
    private javax.swing.JPanel pnlSupAction;
    private javax.swing.JPanel pnlSupList;
    private javax.swing.JLabel pro;
    private javax.swing.JPanel proActive;
    private javax.swing.JPanel proBlink;
    private javax.swing.JLabel proIcon;
    private javax.swing.JLabel proImg;
    private javax.swing.JPanel proLabel;
    private javax.swing.JLayeredPane proLayer;
    private javax.swing.JPanel proPanel;
    private javax.swing.JPanel sidebar;
    private javax.swing.JLabel staff;
    private javax.swing.JPanel staffActive;
    private javax.swing.JPanel staffBlink;
    private javax.swing.JLabel staffIcon;
    private javax.swing.JPanel staffLabel;
    private javax.swing.JLayeredPane staffLayer;
    private javax.swing.JPanel staffPanel;
    private javax.swing.JLabel stock;
    private javax.swing.JLabel stock1;
    private javax.swing.JPanel stockActive;
    private javax.swing.JPanel stockBlink;
    private javax.swing.JLabel stockIcon;
    private javax.swing.JLabel stockIcon1;
    private javax.swing.JPanel stockLabel;
    private javax.swing.JLayeredPane stockLayer;
    private javax.swing.JPanel stockPanel;
    private javax.swing.JPanel supplierActive;
    private javax.swing.JPanel supplierBlink;
    private javax.swing.JLayeredPane supplierLayp;
    private javax.swing.JPanel supplierPanel;
    private javax.swing.JPanel supplierPnl;
    private javax.swing.JTable tblBranch;
    private javax.swing.JTable tblCom;
    private javax.swing.JTable tblPro;
    private javax.swing.JTable tblProCate;
    private javax.swing.JTable tblProVar;
    private javax.swing.JTable tblProduct;
    private javax.swing.JTable tblStock;
    private javax.swing.JTable tblStockCate;
    private javax.swing.JTable tblSup;
    private javax.swing.JTextArea txtBranchAddr;
    private javax.swing.JTextField txtBranchEmail;
    private javax.swing.JTextField txtBranchName;
    private javax.swing.JTextField txtBranchPhone;
    private javax.swing.JTextArea txtComAddres;
    private javax.swing.JTextField txtComEmail;
    private javax.swing.JTextField txtComName;
    private javax.swing.JTextField txtComPhone;
    private javax.swing.JTextField txtProCateDesc;
    private javax.swing.JTextField txtProCateName;
    private javax.swing.JTextField txtProDesc;
    private javax.swing.JTextField txtProName;
    private javax.swing.JTextField txtProSearch;
    private javax.swing.JTextField txtProVarPrice;
    private javax.swing.JTextField txtProVarSearch;
    private javax.swing.JTextField txtProVarSellingPrice;
    private javax.swing.JTextField txtSearchBranch;
    private javax.swing.JTextField txtSearchCom;
    private javax.swing.JTextField txtSearchPro;
    private javax.swing.JTextField txtSearchProCate;
    private javax.swing.JTextField txtSearchStock;
    private javax.swing.JTextField txtSearchStockCate;
    private javax.swing.JTextField txtSearchSup;
    private javax.swing.JTextField txtSearchUser;
    private javax.swing.JTextField txtStockAlertQty;
    private javax.swing.JTextField txtStockCateDesc;
    private javax.swing.JTextField txtStockCateName;
    private javax.swing.JTextField txtStockName;
    private javax.swing.JTextField txtStockPrice;
    private javax.swing.JTextField txtStockQty;
    private javax.swing.JTextArea txtSupAddr;
    private javax.swing.JTextField txtSupEmail;
    private javax.swing.JTextField txtSupName;
    private javax.swing.JTextField txtSupPhone;
    private javax.swing.JPanel userActive;
    private javax.swing.JPanel userBlink;
    private javax.swing.JLabel userIcon;
    private javax.swing.JLayeredPane userLayp;
    private javax.swing.JLabel userLbl;
    private javax.swing.JPanel userPanel;
    private javax.swing.JPanel userPnl;
    private javax.swing.JTable userTbl;
    // End of variables declaration//GEN-END:variables

}
