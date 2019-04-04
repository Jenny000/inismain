package ie.my353.inis.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class CatAndTypeMessageContent {
    private String catAndType;
    private List<String> slotDate;

    public CatAndTypeMessageContent() {
    }

    public CatAndTypeMessageContent(String catAndType, List<String> slotDate) {
        this.catAndType = catAndType;
        this.slotDate = slotDate;
    }


}
