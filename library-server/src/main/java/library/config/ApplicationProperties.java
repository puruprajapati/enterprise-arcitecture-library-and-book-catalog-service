package library.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@ConfigurationProperties(prefix="library-app")
@Validated
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationProperties {
  @NotNull
  private int maxCheckoutBook;
  @NotNull
  private int maxDaysBorrow;
  @NotNull
  private double feePerDay;
  @NotBlank
  private String productCatalogUrl;
  @NotNull
  private int outstandingFeePrintTime;
}
