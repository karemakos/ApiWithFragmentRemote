package com.calc.apiwithfragmentremote.models;

// on video 3 this explanation
import java.util.List;

public class bookModel
{
    private  List <listData> items;

    public List<listData> getItems()
    {
        return items;
    }

    public void setItems(List<listData> items) {
        this.items = items;
    }

   public  class listData
    {
        private volumeInfo volumeInfo;

        public listData(volumeInfo volumeInfo) {
            this.volumeInfo = volumeInfo;
        }

        public  volumeInfo getVolumeInfo() {
            return volumeInfo;
        }

        public void setVolumeInfo(volumeInfo volumeInfo) {
            this.volumeInfo = volumeInfo;
        }

        public class volumeInfo
  {

      private String title;
      private List<String> authors;
      private String publishedDate;
      private String description;
      private images imageLinks;


      public volumeInfo(String title, List<String> authors, String publishedDate, String description, images imageLinks) {
          this.title = title;
          this.authors = authors;
          this.publishedDate = publishedDate;
          this.description = description;
          this.imageLinks = imageLinks;
      }

      public String getTitle() {
          return title;
      }

      public void setTitle(String title) {
          this.title = title;
      }

      public List<String> getAuthors() {
          return authors;
      }

      public void setAuthors(List<String> authors) {
          this.authors = authors;
      }

      public String getPublishedDate() {
          return publishedDate;
      }

      public void setPublishedDate(String publishedDate) {
          this.publishedDate = publishedDate;
      }

      public String getDescription() {
          return description;
      }

      public void setDescription(String description) {
          this.description = description;
      }

      public images getImageLinks() {
          return imageLinks;
      }

      public void setImageLinks(images imageLinks)
      {
          this.imageLinks = imageLinks;
      }

      public class images
      {

          private String thumbnail;

          public images(String thumbnail) {
              this.thumbnail = thumbnail;
          }

          public String getThumbnail() {
              return thumbnail;
          }

          public void setThumbnail(String thumbnail) {
              this.thumbnail = thumbnail;
          }
      }
  }
    }
}

