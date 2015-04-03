package it.tids.seed.webapp.converter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import it.tids.seed.util.ParseUtil;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.richfaces.component.UICalendar;

@FacesConverter("timeStampConverter")
public class TimestampConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Timestamp t = null;
	
		if(ParseUtil.isNotBlank(value) && component instanceof UICalendar){
			UICalendar richCalendar = (UICalendar) component;
			String datePattern = richCalendar.getDatePattern()!=null?richCalendar.getDatePattern():UICalendar.DEFAULT_DATE_PATTERN;
			SimpleDateFormat simpleDateFormat =  new SimpleDateFormat(datePattern);
			try {
				t = new Timestamp(simpleDateFormat.parse(value).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return t;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, 	Object value) {
		
		String t = null;
		
		if(ParseUtil.isNotBlank(value) && component instanceof UICalendar){
			UICalendar richCalendar = (UICalendar) component;
			String datePattern = richCalendar.getDatePattern()!=null?richCalendar.getDatePattern():UICalendar.DEFAULT_DATE_PATTERN;
			SimpleDateFormat simpleDateFormat =  new SimpleDateFormat(datePattern);
			try {
				t = simpleDateFormat.format(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return t;
	}

}