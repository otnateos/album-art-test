package com.example.albumart;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Johanes Soetanto
 * @since 10/05/16
 */
public class AlbumAdapter extends BaseAdapter {
  private final Context context;

  private int[] colors = {
      R.color.fountain_blue, R.color.wild_strawberry,
      R.color.wild_strawberry, R.color.turbo,
      R.color.woodsmoke, R.color.woodsmoke
  };

  private int[] images = {
      R.drawable.image_top_left, R.drawable.image_top_right,
      R.drawable.image_middle_left, R.drawable.image_middle_right,
      R.drawable.image_bottom_left, R.drawable.image_bottom_right
  };

  private int[] textColors = {
      R.color.woodsmoke, R.color.white,
      R.color.white, R.color.woodsmoke,
      R.color.woodsmoke, R.color.woodsmoke
  };

  private int[] starColorFilter = {
      Integer.MIN_VALUE, Color.argb(255, 255, 255, 255),
      Color.argb(255, 255, 255, 255), Integer.MIN_VALUE,
      Integer.MIN_VALUE, Integer.MIN_VALUE
  };

  private float[] textAlpha = {
      0.6f, 1.0f,
      1.0f, 0.6f,
      0.8f, 0.8f
  };

  private String[] artists = {
      "Kodaline", "Fitz and The Tantrums",
      "Jamie Lidell", "Yuna",
      "", ""};
  private String[] titles = {
      "In a Perfect World", "More Than Just a Dream",
      "Jamie Lidell", "Nocturnal",
      "", ""};

  private Album[] albums = new Album[images.length];

  public AlbumAdapter(final Context context) {
    this.context = context;
    for (int i = 0; i < images.length; i++) {
      albums[i] = new Album(colors[i], images[i], starColorFilter[i], textColors[i], textAlpha[i],
          artists[i], titles[i]);
    }
  }

  @Override
  public int getCount() {
    return images.length;
  }

  @Override
  public Object getItem(int position) {
    return null;
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public View getView(int position, View recycledView, ViewGroup parent) {
    final View view;
    if (recycledView == null) {
      view = LayoutInflater.from(context)
          .inflate(R.layout.album_item, parent, false);
      view.setTag(new ViewHolder(view));
    } else {
      view = recycledView;
    }

    setData((ViewHolder)view.getTag(), albums[position]);
    setAnimation(view, position);
    return view;
  }

  private void setAnimation(final View view, final int position) {
    final int startOffset = getGridAnimationOffset(position);
    final Animation slideAnimation = AnimationUtils.loadAnimation(context, R.anim.up_from_bottom);
    slideAnimation.setDuration(startOffset);
    final Animation fadeAnimation =  AnimationUtils.loadAnimation(context, R.anim.fade_in_alpha);
    slideAnimation.setAnimationListener(new NextAnimationListener() {
      @Override
      public void onAnimationEnd(final Animation animation) {
        ((ViewHolder)view.getTag()).layout.startAnimation(fadeAnimation);
      }
    });
    view.startAnimation(slideAnimation);
  }

  private int getGridAnimationOffset(final int position) {
    final int row = position / animationMultiplier();
    final int multiplier = row + animationMultiplier();
    final int reverseRtl = (multiplier - position) * 200;
    return reverseRtl + (row * 300);
  }

  private int animationMultiplier() {
    return context.getResources().getConfiguration()
        .orientation == Configuration.ORIENTATION_LANDSCAPE ? 4 : 2;
  }

  private void setData(final ViewHolder viewHolder, final Album album) {
    viewHolder.artist.setText(album.artist);
    viewHolder.title.setText(album.title);
    viewHolder.cover.setImageResource(album.imageResId);

    setupColorPalette(viewHolder);
//    setupFixedColorPalette(viewHolder, album);
  }

  private void setupColorPalette(final ViewHolder viewHolder) {
    final Bitmap coverImage = ((BitmapDrawable)viewHolder.cover.getDrawable()).getBitmap();
    Palette.from(coverImage)
        .generate(new Palette.PaletteAsyncListener() {
          @Override
          public void onGenerated(final Palette palette) {
            final Palette.Swatch swatch = getSwatch(palette);
            if (swatch != null) {
              viewHolder.layout.setBackgroundColor(swatch.getRgb());
              viewHolder.title.setTextColor(swatch.getTitleTextColor());
              viewHolder.artist.setTextColor(swatch.getTitleTextColor());
              viewHolder.star.setColorFilter(swatch.getBodyTextColor());
            }
          }

          private Palette.Swatch getSwatch(final Palette palette) {
            if (palette.getVibrantSwatch() != null) {
              return palette.getVibrantSwatch();
            }
            return palette.getMutedSwatch();
          }
        });
  }

  @SuppressWarnings("unused")
  private void setupFixedColorPalette(final ViewHolder viewHolder, final Album album) {
    viewHolder.artist.setTextColor(getTextColor(album));
    viewHolder.artist.setAlpha(album.textAlpha);
    viewHolder.title.setTextColor(getTextColor(album));
    viewHolder.title.setAlpha(album.textAlpha);
    viewHolder.layout.setBackgroundResource(album.colorResId);
    if (album.starColorFilter != Integer.MIN_VALUE) {
      viewHolder.star.setColorFilter(album.starColorFilter);
    }
  }

  private int getTextColor(final Album album) {
    return context.getResources().getColor(album.textColorResId);
  }

  static class ViewHolder {
    @Bind(R.id.cover) ImageView cover;
    @Bind(R.id.star) ImageView star;
    @Bind(R.id.artist) TextView artist;
    @Bind(R.id.title) TextView title;
    @Bind(R.id.text_container) View layout;

    public ViewHolder(final View view) {
      ButterKnife.bind(this, view);
    }
  }

  static abstract class NextAnimationListener implements Animation.AnimationListener {
    @Override
    public void onAnimationStart(final Animation animation) {

    }

    @Override
    public void onAnimationRepeat(final Animation animation) {

    }
  }
}
