package com.example.searchlunch.fake

import com.example.searchlunch.model.Genre
import com.example.searchlunch.model.PcPhoto
import com.example.searchlunch.model.Photo
import com.example.searchlunch.model.RestaurantList
import com.example.searchlunch.model.Results
import com.example.searchlunch.model.Shop
import com.example.searchlunch.model.Urls

object FakeDataSource {
    val searchRestaurantList: RestaurantList = RestaurantList(
        results = Results(
            apiVersion = "1.0",
            resultsAvailable = 33286,
            resultsReturned = "50",
            resultsStart = 1,
            shop = listOf(
                Shop(
                    id = "J000999801",
                    name = "信州炉端 串の蔵 新宿東口店",
                    address = "東京都新宿区新宿３-３４-１６池田プラザビルB1F",
                    lat = 35.6904004681,
                    lng = 139.7030126141,
                    genre = Genre(
                        name = "居酒屋",
                        catch = "新宿 居酒屋 焼き鳥 送別会　新宿南口 個室"
                    ),
                    catch = "【個室完備】歓迎会に是非 【信州食材】厳選素材で",
                    access = "JR新宿駅[中央東口]徒歩1分/新宿駅[東口]徒歩1分/新宿三丁目駅丸ノ内線[A5]番出口徒歩1分副都心線[E9］番出口徒歩1分",
                    urls = Urls(
                        pc = "https://www.hotpepper.jp/strJ000999801/?vos=nhppalsa000016"
                    ),
                    photo = Photo(
                        pc = PcPhoto(
                            l = "https://imgfp.hotp.jp/IMGH/17/71/P032051771/P032051771_238.jpg",
                            m = "https://imgfp.hotp.jp/IMGH/17/71/P032051771/P032051771_168.jpg",
                            s = "https://imgfp.hotp.jp/IMGH/17/71/P032051771/P032051771_58_s.jpg"
                        )
                    ),
                    otherMemo = "鏡割り、プロジェクター、宴会場、記念日サプライズなどなど、お気軽にご相談下さい！",
                    shopDetailMemo = "※宴会時人数変更は前日まで！当日キャンセルはご予約分１００%かかります！",
                    budgetMemo = "お通し350円"
                ),
                Shop(
                    id = "J000055036",
                    name = "沖縄料理居酒屋 ニライカナイ 本家 吉祥寺店",
                    address = "東京都武蔵野市吉祥寺本町１－２３－７",
                    lat = 35.7038984821,
                    lng = 139.5818620754,
                    genre = Genre(
                        name = "居酒屋",
                        catch = "泡盛と絶品沖縄料理☆南国式宴会♪"
                    ),
                    catch = "THE!吉祥寺のお洒落なお店 雰囲気抜群◎30名～貸切OK",
                    access = "JR吉祥寺駅中央口　徒歩3分",
                    urls = Urls(
                        pc = "https://www.hotpepper.jp/strJ000055036/?vos=nhppalsa000016"
                    ),
                    photo = Photo(
                        pc = PcPhoto(
                            l = "https://imgfp.hotp.jp/IMGH/38/74/P029063874/P029063874_238.jpg",
                            m = "https://imgfp.hotp.jp/IMGH/38/74/P029063874/P029063874_168.jpg",
                            s = "https://imgfp.hotp.jp/IMGH/38/74/P029063874/P029063874_58_s.jpg"
                        )
                    ),
                    otherMemo = "",
                    shopDetailMemo = "お通し代として400円（税込440円）頂いております",
                    budgetMemo = "お通し代400円（税込440円）"
                )
            )
        )
    )
}