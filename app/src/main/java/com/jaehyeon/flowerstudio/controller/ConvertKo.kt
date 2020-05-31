package com.jaehyeon.flowerstudio.controller

object ConvertKo {

    fun convertKor(label: String): String {

        when(label) {
            "pink primrose" -> return "분홍달맞이꽃" // Oenothera_speciosa
            "hard-leaved pocket orchid" -> return "경엽두란" // Paphiopedilum_micranthum
            "canterbury bells" -> return "종꽃" // Campanula_medium
            "sweet pea" -> return "스위트피"
            "english marigold" -> return "금잔화" // Calendula_officinalis
            "tiger lily" -> return "참나리" // 검색결과 없음
            "moon orchid" -> return "팔레놉시스" // Phalaenopsis_amabilis
            "bird of paradise" -> return "극락조" // Strelitzia
            "monkshood" -> return "투구꽃" // Aconitum
            "globe thistle" -> return "절굿대" // Echinops
            "snapdragon" -> return "금어초속" // Antirrhinum
            "colt's foot" -> return "관동화" // 검색결과 없음
            "king protea" -> return "용왕꽃" // Protea_cynaroides
            "spear thistle" -> return "서양가시엉겅퀴" // Cirsium_vulgare
            "yellow iris" -> return "노랑꽃창포" // Iris pseudacorus
            "globe-flower" -> return "금매화속" // Trollius
            "purple coneflower" -> return "자주루드베키아" // Echinacea_purpurea
            "peruvian lily" -> return "알스트로에메리아" // Alstroemeria
            "balloon flower" -> return "도라지" // Platycodon
            "giant white arum lily" -> return "칼라 릴리" // Zantedeschia_aethiopica
            "fire lily" -> return "불꽃나리" // Gloriosa_(plant)
            "pincushion flower" -> return "스카비오사속" // Chaenactis
            "fritillary" -> return "프리틸라리아"
            "red ginger" -> return "레드 진저" // Alpinia_purpurata
            "grape hyacinth" -> return "무스카리속" // Muscari
            "corn poppy" -> return "개양귀비" // Papaver_rhoeas
            "prince of wales feathers" -> return "웨일즈 왕자의 깃털" // Amaranthus_hypochondriacus
            "stemless gentian" -> return "아카울리스용담" // Gentiana acaulis
            "artichoke" -> return "아티초크"
            "sweet william" -> return "수염패랭이꽃" // Dianthus_barbatus
            "carnation" -> return "카네이션" // Dianthus_caryophyllus
            "garden phlox" -> return "풀협죽도" // Phlox_paniculata
            "love in the mist" -> return "니겔라" // Nigella_damascena
            "mexican aster" -> return "코스모스" // Cosmos_bipinnatus
            "alpine sea holly" -> return "에린지움 알피눔" // Eryngium_alpinum
            "ruby-lipped cattleya" -> return "카틀레야 라비아타 바르 세미알바" // Cattleya_labiata
            "cape flower" -> return "네리네 보우데니" // Plumbago_auriculata
            "great masterwort" -> return "아스트란티아" // Astrantia
            "siam tulip" -> return "쿠르쿠마 아리스마티폴리아" // Curcuma_alismatifolia
            "lenten rose" -> return "렌텐로즈" // Hellebore
            "barbeton daisy" -> return "거베라 야메소니이" // Gerbera_jamesonii
            "daffodil" -> return "수선화속" // Narcissus (plant)
            "sword lily" -> return "글라디올러스" // Gladiolus
            "poinsettia" -> return "포인세티아"
            "bolero deep blue" -> return "리시안서스" // Eustoma_russellianum
            "wallflower" -> return "부지깽이나물" // Erysimum
            "marigold" -> return "매리골드" // Calendula
            "buttercup" -> return "미나리아재비" // Ranunculus
            "oxeye daisy" -> return "프랑스국화" // Leucanthemum_vulgare
            "common dandelion" -> return "민들레" // Taraxacum
            "petunia" -> return "피튜니아"
            "wild pansy" -> return "삼색제비꽃" // Viola_tricolor
            "primula" -> return "앵초속"
            "sunflower" -> return "해바라기" // Helianthus
            "pelargonium" -> return "펠라르고늄"
            "bishop of llandaff" -> return "다알리아" // Dahlia
            "gaura" -> return "가우라"
            "geranium" -> return "펠라르고늄"
            "orange dahlia" -> return "다알리아" // Dahlia_coccinea
            "pink-yellow dahlia" -> return "다알리아" // Dahlia
            "cautleya spicata" -> return "Cautleya spicata"
            "japanese anemone" -> return "대상화" // Anemone_hupehensis
            "black-eyed susan" -> return "검은눈천인국" // Rudbeckia_hirta
            "silverbush" -> return "콘볼불루스 크네오룸" // Argythamnia
            "californian poppy" -> return "금영화" // Eschscholzia_californica
            "osteospermum" -> return "오스테오스페르뭄"
            "spring crocus" -> return "크로쿠스" // Crocus_vernus
            "bearded iris" -> return "아이리스 크로아티아" // Iris_(plant)
            "windflower" -> return "숲바람꽃" // Anemone_nemorosa
            "tree poppy" -> return "Dendromecon" // Dendromecon
            "gazania" -> return "가자니아"
            "azalea" -> return "진달래"
            "water lily" -> return "수련과" // Nymphaeaceae
            "rose" -> return "장미"
            "thorn apple" -> return "독말풀" // Datura
            "morning glory" -> return "나팔꽃"
            "passion flower" -> return "시계꽃속" // Passiflora
            "lotus" -> return "연꽃" // Nelumbo_nucifera
            "toad lily" -> return "뻐꾹나리" // Tricyrtis
            "anthurium" -> return "안투리움"
            "frangipani" -> return "플루메리아" // Plumeria
            "clematis" -> return "클레마티스"
            "hibiscus" -> return "무궁화"
            "columbine" -> return "매발톱속" // Aquilegia
            "desert-rose" -> return "석화" // Rosa_stellata
            "tree mallow" -> return "라바테라" // Lavatera
            "magnolia" -> return "목련"
            "cyclamen" -> return "시클라멘"
            "watercress" -> return "물냉이"
            "canna lily" -> return "인도칸나" // Canna_(plant)
            "hippeastrum" -> return "히페아스트룸"
            "bee balm" -> return "모나르다" // Monarda
            "ball moss" -> return "틸란드시아 레쿠르바타" // Tillandsia_recurvata
            "foxglove" -> return "디기탈리스" // Digitalis
            "bougainvillea" -> return "부겐빌레아"
            "camellia" -> return "동백나무"
            "mallow" -> return "아욱" // Malva_sylvestris
            "mexican petunia" -> return "Ruellia simplex" // Ruellia_simplex
            "bromelia" -> return "브로멜리아"
            "blanket flower" -> return "천인국" // Gaillardia
            "trumpet creeper" -> return "미국 능소화" // Campsis_radicans
            "blackberry lily" -> return "범부채" // Iris_domestica

            else -> {
                return "unknown"
            }
        }
    }
}