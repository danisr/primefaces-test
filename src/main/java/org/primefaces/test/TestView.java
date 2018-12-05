package org.primefaces.test;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "testView")
@ViewScoped
public class TestView implements Serializable {
    
    private String originField;
    private List<String> fields;
    private List<String> columns;
    private String regex;
    
    @PostConstruct  
    public void init(){
        originField = "STRING";
        fields = new ArrayList<String>();
        fields.add(originField);
        columns = new ArrayList<String>();
        columns.add("NAME");
        regex = "^([A-Z 0-9])+$";
    }
          
    public void onCellEdit(CellEditEvent event) {
        FacesMessage message = null;

        if(validator()) { /* VALID */

            //SAVE to DataBase.

        }else { /* NOT VALID */
            fields.clear();
            fields.add(originField);
            message = new FacesMessage(localeBean.getBundle("pattern.validator") + regex);
        }

        RequestContext.getCurrentInstance().update("f-form:fields");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public boolean validator() {    	
        boolean validator = true;

        String expressionToMatch = fields.get(0);
        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(expressionToMatch);

        if(!mat.matches()) { /* REGEX not valid */
            validator = false; 
        }
        return validator;
    }
}
