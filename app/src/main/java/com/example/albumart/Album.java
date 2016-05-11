package com.example.albumart;

/**
 * @author Johanes Soetanto
 * @since 10/05/16
 */
public class Album {

  int colorResId;
  int imageResId;
  int starColorFilter;
  int textColorResId;
  float textAlpha;
  String artist;
  String title;

  public Album() {}

  public Album(int colorResId, int imageResId, int starColorFilter, int textColorResId, float textAlpha, String artist, String title) {
    this.colorResId = colorResId;
    this.imageResId = imageResId;
    this.starColorFilter = starColorFilter;
    this.textColorResId = textColorResId;
    this.textAlpha = textAlpha;
    this.artist = artist;
    this.title = title;
  }
}
