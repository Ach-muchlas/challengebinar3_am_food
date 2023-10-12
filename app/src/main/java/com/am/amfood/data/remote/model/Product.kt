package com.am.amfood.data.remote.model
import android.os.Parcelable
import com.am.amfood.R
import kotlinx.parcelize.Parcelize

@Parcelize
open class Product(
    val name: String,
    val rate: Double,
    val price: Double,
    val photo: Int,
    val desc: String? = null,
    val location: String? = null,
    val imageProduct: Int,
    val lat: Double? = null,
    val long: Double? = null,
) : Parcelable

val dummyDataCard = arrayListOf(
    Product(
        "Beef Burger",
        5.0,
        18000.0,
        R.drawable.beef_burger,
        "Beef Burger is a delightful dish that captivates with the succulence of fresh beef, perfectly grilled and adorned with special sauce, crisp vegetables, and velvety cheese. Each bite delivers an unmatched savory and juicy flavor. This burger is the perfect combination of crispy texture and the natural pleasure of beef, making it a favorite choice for burger enthusiasts.",
        "Jl. A Yani Frontage Barat No.16-18, Wonokromo, Kec. Wonokromo, Surabaya, Jawa Timur 60243",
        R.drawable.burger_detail,
        -7.309267553447969, 112.73451225824547
    ),
    Product(
        "Chicken Burger",
        5.0,
        16500.0,
        R.drawable.chicken_burger,
        "Chicken Burger is a mouthwatering dish featuring tender chicken pieces, perfectly fried with special seasonings, and served in a delightful, soft bun. Each bite combines the savory taste of chicken with the crispy sensation of the bread, accompanied by refreshing sauce and fresh vegetables that add perfect flavors and textures.",
        "Jl. Raya Lontar No.2, Babatan, Kec. Wiyung, Surabaya, Jawa Timur 60123",
        R.drawable.burger_detail,
        -7.2890670922445135,
        112.67524545117689
    ),
    Product(
        "Cheese Burger",
        4.5,
        10000.0,
        R.drawable.cheese_burger,
        "Cheese Burger is a captivating burger dish with the addition of smooth, melted cheese that imparts a savory and delightful touch to perfectly grilled beef. With every bite, you'll experience the harmonious blend of juicy meat, layered cheese, and enticingly fresh sauce and vegetables that tantalize the taste buds.",
        "Jl. Jenderal Basuki Rachmat No.8-12, Kedungdoro, Kec. Tegalsari, Surabaya, Jawa Timur 60261",
        R.drawable.burger_detail,
        -7.263449316189823, 112.74077437142043
    ),
    Product(
        "Fish Burger",
        4.0,
        20000.0,
        R.drawable.fish_burger,
        "Fish Burger is a delightful dish featuring the deliciousness of freshly fried golden fish, served in a soft burger bun. Inspired by the sea, this burger brings the savory flavor of fish together with the tenderness of the bread, complemented by a special sauce and fresh vegetables.hom",
        "Jl. Raya Laguna KJW Putih Tambak No.2, Kejawaan Putih Tamba, Kec. Mulyorejo, Surabaya, Jawa Timur 60112",
        R.drawable.burger_detail,
        -7.271276724001825, 112.80502808430249
    ),
    Product(
        "Cadian Taco",
        3.5,
        21000.0,
        R.drawable.canadian_taco,
        "\"Canadian taco\" is a term that is more of a culinary creativity than an official dish in Canadian cuisine. It is a variation or fusion of tacos that combines elements from Canadian cuisine with the traditional Mexican taco concept",
        "Jl. Jenderal Basuki Rachmat No.8-12, Kedungdoro, Kec. Tegalsari, Surabaya, Jawa Timur 60261",
        R.drawable.burger_detail,
        -7.2891180, 112.6551310
    ),
    Product(
        "Purple Rain",
        4.5,
        10500.0,
        R.drawable.purple_rain_cocktail,
        "\"Canadian taco\" is a term that is more of a culinary creativity than an official dish in Canadian cuisine. It is a variation or fusion of tacos that combines elements from Canadian cuisine with the traditional Mexican taco concept",
        "Jl. Nias No.110, Gubeng, Kec. Gubeng, Surabaya, Jawa Timur 60281",
        R.drawable.cocktail,
        -7.27587103166437, 112.75016162419656
    ),
)

