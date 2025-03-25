package bitc.fullstack503.moneylog_spring.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
public class CategoryDTO
{
  @JsonProperty ("categorySeq")
  private int categorySeq;
  @JsonProperty ("categoryName")
  private String categoryName;
  @JsonProperty ("categoryIsDefault")
  private boolean categoryIsDefault;
  @JsonProperty ("memberId")
  private String memberId;
}
