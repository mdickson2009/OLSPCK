package uuproject.olspck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Image_Upload extends AppCompatActivity {

    public Image_Upload(String s, String s1) {
    }

    public class ImageUpload {

        public String name;
        public String url;

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }

        public ImageUpload(String name, String url) {
            this.name = name;
            this.url = url;
        }

        public ImageUpload() {
        }
    }
}
