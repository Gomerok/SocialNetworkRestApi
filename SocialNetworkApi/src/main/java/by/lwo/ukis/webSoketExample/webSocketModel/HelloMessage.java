package by.lwo.ukis.webSoketExample.webSocketModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelloMessage {
    private Long id;
    private String name;

}