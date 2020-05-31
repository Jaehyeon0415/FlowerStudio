package com.jaehyeon.flowerstudio.controller

object ReLabel {

    fun relabel(fName: String): String {

        when(fName) {
            "pink primrose" -> return "Oenothera_speciosa" // Oenothera_speciosa
            "hard-leaved pocket orchid" -> return "Paphiopedilum_micranthum" // Paphiopedilum_micranthum
            "canterbury bells" -> return "Campanula_medium" // Campanula_medium
            "sweet pea" -> return "sweet_pea"
            "english marigold" -> return "Calendula_officinalis" // Calendula_officinalis
            "tiger lily" -> return "참나리" // 검색결과 없음
            "moon orchid" -> return "Phalaenopsis_amabilis" // Phalaenopsis_amabilis
            "bird of paradise" -> return "Strelitzia" // Strelitzia
            "monkshood" -> return "Aconitum" // Aconitum
            "globe thistle" -> return "Echinops" // Echinops
            "snapdragon" -> return "Antirrhinum" // Antirrhinum
            "colt's foot" -> return "관동화" // 검색결과 없음
            "king protea" -> return "Protea_cynaroides" // Protea_cynaroides
            "spear thistle" -> return "Cirsium_vulgare" // Cirsium_vulgare
            "yellow iris" -> return "Iris_pseudacorus" // Iris_pseudacorus
            "globe-flower" -> return "Trollius" // Trollius
            "purple coneflower" -> return "Echinacea_purpurea" // Echinacea_purpurea
            "peruvian lily" -> return "Alstroemeria" // Alstroemeria
            "balloon flower" -> return "Platycodon" // Platycodon
            "giant white arum lily" -> return "Zantedeschia_aethiopica" // Zantedeschia_aethiopica
            "fire lily" -> return "Gloriosa_(plant)" // Gloriosa_(plant)
            "pincushion flower" -> return "Chaenactis" // Chaenactis
            "fritillary" -> return "fritillary"
            "red ginger" -> return "Alpinia_purpurata" // Alpinia_purpurata
            "grape hyacinth" -> return "Muscari" // Muscari
            "corn poppy" -> return "Papaver_rhoeas" // Papaver_rhoeas
            "prince of wales feathers" -> return "Amaranthus_hypochondriacus" // Amaranthus_hypochondriacus
            "stemless gentian" -> return "Gentiana_acaulis" // Gentiana_acaulis
            "artichoke" -> return "artichoke"
            "sweet william" -> return "Dianthus_barbatus" // Dianthus_barbatus
            "carnation" -> return "Dianthus_caryophyllus" // Dianthus_caryophyllus
            "garden phlox" -> return "Phlox_paniculata" // Phlox_paniculata
            "love in the mist" -> return "Nigella_damascena" // Nigella_damascena
            "mexican aster" -> return "Cosmos_bipinnatus" // Cosmos_bipinnatus
            "alpine sea holly" -> return "Eryngium_alpinum" // Eryngium_alpinum
            "ruby-lipped cattleya" -> return "Cattleya_labiata" // Cattleya_labiata
            "cape flower" -> return "Plumbago_auriculata" // Plumbago_auriculata
            "great masterwort" -> return "Astrantia" // Astrantia
            "siam tulip" -> return "Curcuma_alismatifolia" // Curcuma_alismatifolia
            "lenten rose" -> return "Hellebore" // Hellebore
            "barbeton daisy" -> return "Gerbera_jamesonii" // Gerbera_jamesonii
            "daffodil" -> return "Narcissus (plant)" // Narcissus (plant)
            "sword lily" -> return "Gladiolus" // Gladiolus
            "poinsettia" -> return "poinsettia"
            "bolero deep blue" -> return "Eustoma_russellianum" // Eustoma_russellianum
            "wallflower" -> return "Erysimum" // Erysimum
            "marigold" -> return "Calendula" // Calendula
            "buttercup" -> return "Ranunculus" // Ranunculus
            "oxeye daisy" -> return "Leucanthemum_vulgare" // Leucanthemum_vulgare
            "common dandelion" -> return "Taraxacum" // Taraxacum
            "petunia" -> return "petunia"
            "wild pansy" -> return "Viola_tricolor" // Viola_tricolor
            "primula" -> return "primula"
            "sunflower" -> return "Helianthus" // Helianthus
            "pelargonium" -> return "pelargonium"
            "bishop of llandaff" -> return "Dahlia" // Dahlia
            "gaura" -> return "gaura"
            "geranium" -> return "geranium"
            "orange dahlia" -> return "Dahlia_coccinea" // Dahlia_coccinea
            "pink-yellow dahlia" -> return "Dahlia" // Dahlia
            "cautleya spicata" -> return "Cautleya_spicata"
            "japanese anemone" -> return "Anemone_hupehensis" // Anemone_hupehensis
            "black-eyed susan" -> return "Rudbeckia_hirta" // Rudbeckia_hirta
            "silverbush" -> return "Argythamnia" // Argythamnia
            "californian poppy" -> return "Eschscholzia_californica" // Eschscholzia_californica
            "osteospermum" -> return "osteospermum"
            "spring crocus" -> return "Crocus_vernus" // Crocus_vernus
            "bearded iris" -> return "Iris_(plant)" // Iris_(plant)
            "windflower" -> return "Anemone_nemorosa" // Anemone_nemorosa
            "tree poppy" -> return "Dendromecon" // Dendromecon
            "gazania" -> return "gazania"
            "azalea" -> return "azalea"
            "water lily" -> return "Nymphaeaceae" // Nymphaeaceae
            "rose" -> return "rose"
            "thorn apple" -> return "Datura" // Datura
            "morning glory" -> return "morning_glory"
            "passion flower" -> return "Passiflora" // Passiflora
            "lotus" -> return "Nelumbo_nucifera" // Nelumbo_nucifera
            "toad lily" -> return "Tricyrtis" // Tricyrtis
            "anthurium" -> return "anthurium"
            "frangipani" -> return "Plumeria" // Plumeria
            "clematis" -> return "clematis"
            "hibiscus" -> return "hibiscus"
            "columbine" -> return "Aquilegia" // Aquilegia
            "desert-rose" -> return "Rosa_stellata" // Rosa_stellata
            "tree mallow" -> return "Lavatera" // Lavatera
            "magnolia" -> return "magnolia"
            "cyclamen" -> return "cyclamen"
            "watercress" -> return "watercress"
            "canna lily" -> return "Canna_(plant)" // Canna_(plant)
            "hippeastrum" -> return "hippeastrum"
            "bee balm" -> return "Monarda" // Monarda
            "ball moss" -> return "Tillandsia_recurvata" // Tillandsia_recurvata
            "foxglove" -> return "Digitalis" // Digitalis
            "bougainvillea" -> return "bougainvillea"
            "camellia" -> return "camellia"
            "mallow" -> return "Malva_sylvestris" // Malva_sylvestris
            "mexican petunia" -> return "Ruellia_simplex" // Ruellia_simplex
            "bromelia" -> return "bromelia"
            "blanket flower" -> return "Gaillardia" // Gaillardia
            "trumpet creeper" -> return "Campsis_radicans" // Campsis_radicans
            "blackberry lily" -> return "Iris_domestica" // Iris_domestica

            else -> {
                return "unknown"
            }
        }
    }
}