package it.tids.seed.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the volume database table.
 * 
 */
@Entity
@Table(name = "measures")
public class Measure implements Serializable
{
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "measure_id", insertable = false, updatable = false)
   private Integer id;

   @Temporal(TemporalType.DATE)
   @Column(name = "measure_date", updatable = false)
   private Date date;

   @Column(name = "measure_value")
   private int value;

   @ManyToOne
   @JoinColumn(name = "device_id")
   private Device device;

   public Measure()
   {
   }

   public Integer getId()
   {
      return this.id;
   }

   public void setId(Integer id)
   {
      this.id = id;
   }

   public Date getDate()
   {
      return this.date;
   }

   public void setDate(Date date)
   {
      this.date = date;
   }

   public int getValue()
   {
      return this.value;
   }

   public void setValue(int value)
   {
      this.value = value;
   }

   public Device getDevice()
   {
      return this.device;
   }

   public void setDevice(Device device)
   {
      this.device = device;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((date == null) ? 0 : date.hashCode());
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      result = prime * result + ((device == null) ? 0 : device.hashCode());
      result = prime * result + value;
      return result;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      Measure other = (Measure) obj;
      if (date == null)
      {
         if (other.date != null)
            return false;
      }
      else if (!date.equals(other.date))
         return false;
      if (id == null)
      {
         if (other.id != null)
            return false;
      }
      else if (!id.equals(other.id))
         return false;
      if (device == null)
      {
         if (other.device != null)
            return false;
      }
      else if (!device.equals(other.device))
         return false;
      if (value != other.value)
         return false;
      return true;
   }

   @Override
   public String toString()
   {
      return "Volume [id=" + id + ", date=" + date + ", value=" + value
            + ", device=" + device + "]";
   }

}