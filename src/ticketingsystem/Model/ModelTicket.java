/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ticketingsystem.Model;

import java.time.LocalDateTime;
import java.util.Date;
import javax.swing.Icon;


public class ModelTicket {

    /**
     * @return the qrImage
     */
    public Icon getQrImage() {
        return qrImage;
    }

    /**
     * @param qrImage the qrImage to set
     */
    public void setQrImage(Icon qrImage) {
        this.qrImage = qrImage;
    }

    /**
     * @return the scheduleDate
     */
    public LocalDateTime getScheduleDate() {
        return scheduleDate;
    }

    /**
     * @param scheduleDate the scheduleDate to set
     */
    public void setScheduleDate(LocalDateTime scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

  
    public String getConcertName() {
        return concertName;
    }

 
    public void setConcertName(String concertName) {
        this.concertName = concertName;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

  
    public String getSeats() {
        return seats;
    }

  
    public void setSeats(String seats) {
        this.seats = seats;
    }

 


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ModelTicket(String concertName, String ticketCode, String seats, LocalDateTime scheduleDate, double price, Icon qrImage) {
        this.concertName = concertName;
        this.ticketCode = ticketCode;
        this.seats = seats;
        this.scheduleDate = scheduleDate;
        this.price = price;
        this.qrImage = qrImage;
    }

 



    public ModelTicket() {
    }
    
    private String concertName;
    private String ticketCode;
    private String seats;
    private LocalDateTime scheduleDate;
    private double price;
    private Icon qrImage;
}
