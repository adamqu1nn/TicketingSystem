
package ticketingsystem.Controller;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import ticketingsystem.JDBC.DatabaseConnection;
import ticketingsystem.Model.ModelTicket;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import ticketingsystem.Main.Main;

public class BookTickerController {
    private PreparedStatement p;
    private ResultSet rs;
    private Main main;
     private DefaultTableCellRenderer centerRenderer;
    public BookTickerController() throws SQLException {
        centerRenderer = new DefaultTableCellRenderer();
        
       
    }
   
    public void BookTicket(ModelTicket data) throws SQLException{
        try {
            
             DatabaseConnection connection = new DatabaseConnection();
             Connection con = connection.getCConnection();
           String sql = "insert into bookticket(TicketCode,ConcertName,Seats,ScheduleDate,Price,qrImage)values(?,?,?,?,?,?)"; 
           p = con.prepareStatement(sql);
           p.setString(1, data.getTicketCode());
           p.setString(2, data.getConcertName());
           p.setString(3, data.getSeats());
           p.setTimestamp(4, java.sql.Timestamp.valueOf(data.getScheduleDate()));
           p.setDouble(5, data.getPrice());
          BufferedImage bufferedImage = new BufferedImage(data.getQrImage().getIconWidth(), data.getQrImage().getIconHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.createGraphics();
        data.getQrImage().paintIcon(null, g, 0, 0);
        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        byte[] imageBytes = baos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
        p.setBlob(6, bais, imageBytes.length);
           
           p.execute();
           JOptionPane.showMessageDialog(null, "Succesfully Added");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (p != null) {
                p.close();
            }
        }
    }
    public void populateData(JTable table) throws SQLException{
        try {
             DatabaseConnection connection = new DatabaseConnection();
             Connection con = connection.getCConnection();
             DefaultTableModel model = (DefaultTableModel)table.getModel();
             model.setRowCount(0);
             
             String sql = "SELECT * FROM bookticket";
             p = con.prepareStatement(sql);
             
             rs = p.executeQuery();
             while (rs.next()) {                
                 Vector <Object>v = new Vector<>(); 
                 for (int i = 0; i < 35; i++) {
                     v.add(rs.getInt("id"));
                     v.add(rs.getString("TicketCode"));
                     v.add(rs.getString("ConcertName"));
                     v.add(rs.getString("Seats"));
                     v.add(rs.getTimestamp("ScheduleDate"));
                     v.add(rs.getDouble("Price"));
                     Blob blob = rs.getBlob("qrImage");   
                       table.getColumnModel().getColumn(6).setCellRenderer(new ImageIconCellRenderer());
                   ImageIcon imageicon = blobToImageIcon(blob,70,70);
                   v.add(imageicon);
                 }
                 model.addRow(v);
            }
          
            

             
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (p!=null) {
                p.close();
            }
        }
    }
  
      class ImageIconCellRenderer extends DefaultTableCellRenderer {
        @Override
        protected void setValue(Object value) {
            if (value instanceof ImageIcon) {
                setIcon((ImageIcon) value);
            } else {
                setText((value == null) ? "" : value.toString());
                setIcon(null);
            }
        }
    }
      private ImageIcon blobToImageIcon(Blob blob, int width, int height) throws SQLException {
        if (blob != null) {
            try (InputStream inputStream = blob.getBinaryStream()) {
                // Read the bytes from the blob
                byte[] bytes = inputStream.readAllBytes();
                // Convert bytes to ImageIcon
                ImageIcon originalIcon = new ImageIcon(bytes);
                // Scale down the image
                Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
