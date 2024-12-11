package DTO;


import com.google.gson.annotations.Expose;
import entity.Option;

import java.util.List;


/**
 * Author: Kiet Mai Tran Tuan
 * Created: 17/04/2024 10:16 SA
 * Project name: Java_SGU
 * “Family is where life begins and love never ends.”
 */


public class OptionDTO {
    @Expose
    private int totalOptions;
    @Expose
    private List<Option> options;
 
    
    public int getTotalOptions() {
        return totalOptions;
    }
    
    public void setTotalOptions(int totalOptions) {
        this.totalOptions = totalOptions;
    }
    
    public List<Option> getOptions() {
        return options;
    }
    
    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
