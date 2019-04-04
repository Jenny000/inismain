package ie.my353.inis.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Data
public class SlotCatAndTypeMessageContent {
    private Set<String> catAndType;

    public SlotCatAndTypeMessageContent(Set<String> catAndType) {
        this.catAndType = catAndType;
    }

    public SlotCatAndTypeMessageContent() {

    }

    @Override
    public String toString() {
        return "SlotCatAndTypeMessageContent{" +
                "catAndType=" + catAndType +
                '}';
    }
}
