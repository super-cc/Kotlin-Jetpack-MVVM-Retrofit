package love.isuper.core.glide;

import androidx.annotation.NonNull;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.BaseRequestOptions;

import love.isuper.core.R;

/**
 * Created by shichao on 2022/6/6.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
@GlideExtension
public class MyGlideExtension {

    private MyGlideExtension() {
    } // utility class

    @NonNull
    @GlideOption
    public static BaseRequestOptions<?> applyDefaultImage(BaseRequestOptions<?> options) {
        return options
                .centerCrop()
                .placeholder(R.drawable.shape_default_icon)
                .error(R.drawable.shape_default_icon)
                .format(DecodeFormat.PREFER_RGB_565);
    }

    @NonNull
    @GlideOption
    public static BaseRequestOptions<?> applyAppAvatar(BaseRequestOptions<?> options) {
        return options
                .centerCrop()
                .transform(new RoundedCorners(30))
                .placeholder(R.drawable.shape_default_icon)
                .error(R.drawable.shape_default_icon)
                .format(DecodeFormat.PREFER_RGB_565);
    }

}