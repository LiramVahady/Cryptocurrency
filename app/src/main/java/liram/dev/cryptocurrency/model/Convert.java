package liram.dev.cryptocurrency.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class Convert {

    @SerializedName("data")
    private List<ConvertDetails> convertDetails;

    public List<ConvertDetails> getConvertDetails() {
        return convertDetails;
    }

    public void setConvertDetails(List<ConvertDetails> convertDetails) {
        this.convertDetails = convertDetails;
    }

    @Override
    public String toString() {
        return "Convert{" +
                "convertDetails=" + convertDetails +
                '}';
    }

    public class ConvertDetails {

        @SerializedName("code")
        private String code;
        @SerializedName("country")
        private String countryName;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        @Override
        public String toString() {
            return "Convert{" +
                    "code='" + code + '\'' +
                    ", countryName='" + countryName + '\'' +
                    '}';
        }
    }
}