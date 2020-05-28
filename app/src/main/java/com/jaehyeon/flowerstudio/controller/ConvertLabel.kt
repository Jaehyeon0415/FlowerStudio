package com.jaehyeon.flowerstudio.controller

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView

object ConvertLabel {

    fun ConvertKor(fName: String): String {

        when(fName) {
            "pink primrose" -> return "분홍달맞이꽃"
            "hard-leaved pocket orchid" -> return "경엽두란"
            "canterbury bells" -> return "메디움초롱꽃"
            "sweet pea" -> return "스위트피"
            "english marigold" -> return "금잔화"
            "tiger lily" -> return "참나리"
            "moon orchid" -> return "팔레놉시스 아마빌리스"
            "bird of paradise" -> return "극락조"
            "monkshood" -> return "투구꽃속"
            "globe thistle" -> return "절굿대속"
            "snapdragon" -> return "금어초속"
            "colt's foot" -> return "관동화"
            "king protea" -> return "용왕꽃"
            "spear thistle" -> return "서양가시엉겅퀴"
            "yellow iris" -> return "노랑꽃창포"
            "globe-flower" -> return "금매화속"
            "purple coneflower" -> return "자주루드베키아"
            "peruvian lily" -> return "알스트로에메리아속"
            "balloon flower" -> return "도라지"
            "giant white arum lily" -> return "칼라 릴리"
            "fire lily" -> return "불꽃나리"
            "pincushion flower" -> return "스카비오사속"
            "fritillary" -> return "프리틸라리아"
            "red ginger" -> return "레드 진저"
            "grape hyacinth" -> return "무스카리속"
            "corn poppy" -> return "개양귀비"
            "prince of wales feathers" -> return "웨일즈 왕자의 깃털"
            "stemless gentian" -> return "아카울리스용담"
            "artichoke" -> return "아티초크"
            "sweet william" -> return "수염패랭이꽃"
            "carnation" -> return "카네이션"
            "garden phlox" -> return "풀협죽도"
            "love in the mist" -> return "니겔라"
            "mexican aster" -> return "코스모스"
            "alpine sea holly" -> return "에린지움 알피눔"
            "ruby-lipped cattleya" -> return "카틀레야 라비아타 바르 세미알바"
            "cape flower" -> return "네리네 보우데니"
            "great masterwort" -> return "아스트란티아"
            "siam tulip" -> return "쿠르쿠마 아리스마티폴리아"
            "lenten rose" -> return "렌텐로즈"
            "barbeton daisy" -> return "거베라 야메소니이"
            "daffodil" -> return "수선화속"
            "sword lily" -> return "글라디올러스"
            "poinsettia" -> return "포인세티아"
            "bolero deep blue" -> return "리시안서스"
            "wallflower" -> return "부지깽이나물"
            "marigold" -> return "매리골드"
            "buttercup" -> return "미나리아재비"
            "oxeye daisy" -> return "프랑스국화"
            "common dandelion" -> return "민들레"
            "petunia" -> return "피튜니아"
            "wild pansy" -> return "삼색제비꽃"
            "primula" -> return "앵초속"
            "sunflower" -> return "해바라기"
            "pelargonium" -> return "펠라르고늄"
            "bishop of llandaff" -> return "다알리아"
            "gaura" -> return "가우라"
            "geranium" -> return "펠라르고늄"
            "orange dahlia" -> return "다알리아"
            "pink-yellow dahlia" -> return "다알리아"
            "cautleya spicata" -> return "Cautleya spicata"
            "japanese anemone" -> return "대상화"
            "black-eyed susan" -> return "검은눈천인국"
            "silverbush" -> return "콘볼불루스 크네오룸"
            "californian poppy" -> return "금영화"
            "osteospermum" -> return "오스테오스페르뭄"
            "spring crocus" -> return "크로쿠스"
            "bearded iris" -> return "아이리스 크로아티아"
            "windflower" -> return "숲바람꽃"
            "tree poppy" -> return "Dendromecon"
            "gazania" -> return "가자니아"
            "azalea" -> return "진달래"
            "water lily" -> return "수련과"
            "rose" -> return "장미"
            "thorn apple" -> return "독말풀"
            "morning glory" -> return "나팔꽃"
            "passion flower" -> return "시계꽃속"
            "lotus" -> return "연꽃"
            "toad lily" -> return "뻐꾹나리"
            "anthurium" -> return "안투리움"
            "frangipani" -> return "플루메리아"
            "clematis" -> return "클레마티스"
            "hibiscus" -> return "무궁화"
            "columbine" -> return "매발톱속"
            "desert-rose" -> return "석화"
            "tree mallow" -> return "라바테라"
            "magnolia" -> return "목련"
            "cyclamen" -> return "시클라멘"
            "watercress" -> return "물냉이"
            "canna lily" -> return "인도칸나"
            "hippeastrum" -> return "히페아스트룸"
            "bee balm" -> return "모나르다"
            "ball moss" -> return "틸란드시아 레쿠르바타"
            "foxglove" -> return "디기탈리스"
            "bougainvillea" -> return "부겐빌레아"
            "camellia" -> return "동백나무"
            "mallow" -> return "아욱"
            "mexican petunia" -> return "Ruellia simplex"
            "bromelia" -> return "브로멜리아"
            "blanket flower" -> return "천인국"
            "trumpet creeper" -> return "미국 능소화"
            "blackberry lily" -> return "범부채"

            else -> {
                return "unknown"
            }
        }
    }
}