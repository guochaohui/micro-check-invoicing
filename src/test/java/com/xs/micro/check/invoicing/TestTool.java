package com.xs.micro.check.invoicing;

import com.google.common.base.Splitter;
import org.apache.commons.io.IOUtils;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {MicroCheckInvoicingApplication.class})
public class TestTool {

    @Test
    public void test() throws Throwable {
        String t = "INSERT INTO `app_area` (`id`, `parent_id`, `region_path`, `region_grade`, `local_name`) VALUES (%d, %d, ',%s,', %d, '%s');";
        String[] p_array = new String[]{"BALI", "BANGKA BELITUNG", "BANTEN", "BENGKULU", "D.I. YOGYAKARTA", "DKI JAKARTA", "GORONTALO", "JAMBI", "JAWA BARAT", "JAWA TENGAH", "JAWA TIMUR", "KALIMANTAN BARAT", "KALIMANTAN SELATAN", "KALIMANTAN TENGAH", "KALIMANTAN TIMUR", "KALIMANTAN UTARA", "KEPULAUAN RIAU", "LAMPUNG", "MALUKU", "MALUKU UTARA", "NANGGROE ACEH DARUSSALAM", "NUSA TENGGARA BARAT", "NUSA TENGGARA TIMUR", "PAPUA", "PAPUA BARAT", "RIAU", "SULAWESI BARAT", "SULAWESI SELATAN", "SULAWESI TENGAH", "SULAWESI TENGGARA", "SULAWESI UTARA", "SUMATERA BARAT", "SUMATERA SELATAN", "SUMATERA UTARA"};
        String[] c_array = new String[34];
        c_array[33] = "NIAS SELATAN,PADANG LAWAS UTARA,LANGKAT,HUMBANG HASUNDUTAN,DELI SERDANG,SAMOSIR,KARO,ASAHAN,LABUAN BATU,DAIRI,MANDAILING NATAL,LUBUK PAKAM,SIPIROK,STABAT,LABUHAN BATU SELATAN,TEBING TINGGI,TOBA SAMOSIR,TAPANULI UTARA,TAPANULI TENGAH,TAPANULI SELATAN,LABUHAN BATU UTARA,TANJUNG BALAI,BATU BARA,BINJAI,SIMALUNGUN,MEDAN,NIAS BARAT,SERDANG BEDAGAI,GUNUNG SITOLI,NIAS UTARA,PAKPAK BHARAT,PADANG LAWAS,PEMATANG SIANTAR,SIBOLGA";
        c_array[32] = "LAHAT,BANYUASIN,EMPAT LAWANG,KAYU AGUNG,PAGAR ALAM,BATURAJA,MUARA ENIM,OGAN KOMERING ULU TIMUR,SEKAYU,PALEMBANG,MUARA DUA,PENUKAL ABAB LEMATANG ILIR,OGAN ILIR,PRABUMULIH,MUSI RAWAS,MUSI RAWAS UTARA";
        c_array[31] = "TANAH DATAR,PASAMAN,LIMAPULUH KOTA,DHAMASRAYA,SOLOK SELATAN,PADANG,PAYAKUMBUH,PESISIR SELATAN,SIJUNJUNG,PARIAMAN,AROSUKA,BUKIT TINGGI,SOLOK,LUBUK BASUNG,PADANG PANJANG,PADANG PARIAMAN,SAWAH LUNTO,PASAMAN BARAT,MENTAWAI";
        c_array[30] = "MINAHASA,BOLAANG MONGONDOW,KOTA MOBAGU,MINAHASA TENGGARA,MINAHASA SELATAN,MINAHASA UTARA,TAHUNA,BITUNG,BOLAANG MONGONDOW TIMUR,BOLAANG MONGONDOW UTARA,TOMOHON,MANADO,KEPULAUAN TALAUD,MONGONDOW SELATAN,KEPULAUAN SIAU";
        c_array[29] = "KONAWE SELATAN,BUTON UTARA,TIRAWUTA,KOLAKA,LANGARA,BOMBANA,BAU-BAU,KONAWE,MUNA,BATAUGA,LABUNGKARI,SAREWIGADI,WAKATOBI,KONAWE UTARA,BUTON,KOLAKA UTARA,KENDARI";
        c_array[28] = "TOJO UNA-UNA,POSO,TOLI-TOLI,MOROWALI,BUOL,MOROWALI UTARA,PARIGI MOUTONG,SIGI,BANGGAI,BANGGAI LAUT,DONGGALA,BANGGAI KEPULAUAN,PALU";
        c_array[27] = "TANA TORAJA,WAJO,PINRANG,GOWA,PANGKAJENE,PARE PARE,SIDENRENG RAPANG,MALILI,BANTAENG,MASAMBA,MAROS,TAKALAR,SELAYAR,BULUKUMBA,MAKASSAR,SINJAI,PALOPO,LUWU,BARRU,BONE,TORAJA UTARA,ENREKANG,JENEPONTO,SOPPENG";
        c_array[26] = "MAMASA,MAJENE,PASANG KAYU,TOBADAK,MAMUJU,POLEWALI";
        c_array[25] = "ROKAN HILIR,KAMPAR,DUMAI,ROKAN HULU,BENGKALIS,PEKANBARU,SIAK,INDRAGIRI HULU,KEPULAUAN MERANTI,INDRAGIRI HILIR,PELALAWAN";
        c_array[24] = "MANOKWARI,TELUK WONDAMA,TAMBRAUW,KAIMANA,KAB.SORONG,FAK-FAK,TELUK BINTUNI,RAJA AMPAT,SORONG,SORONG SELATAN,MANOKWARI SELATAN,MAYBRAT";
        c_array[23] = "PUNCAK JAYA,YAHUKIMO,NDUGA,ARFAK,ASMAT,YALIMO,YAPEN WAROPEN,SORENDIWERI,JAYAPURA,JAYAWIJAYA,NABIRE,DOGIYAI,KEEROM,DEIYAI,WAROPEN,LANNY JAYA,SARMI,INTAN JAYA,MERAUKE,PANIAI,TOLIKARA,SENTANI,PUNCAK,MIMIKA,BOVEN DIGOEL,SERUI,PEGUNUNGAN BINTANG,BIAK,MAPPI,MAMBERAMO TENGAH,MAMBERAMO RAYA";
        c_array[22] = "LARANTUKA,KUPANG,SABU RAIJUA,LEWOLEBA,BAJAWA,BORONG,OELAMASI,KALABAHI,TIMOR TENGAH UTARA,BELU,LABUAN BAJO,MALAKA,BAA,ENDE,TAMBOLAKA,SOE,RUTENG,WAINGAPU,WAIKABUBAK,MAUMERE,MBAY,WAIBAKUL";
        c_array[21] = "KAB.BIMA,SUMBAWA BARAT,LOMBOK,SUMBAWA BESAR,BIMA,LOMBOK UTARA,MATARAM,SELONG,LOMBOK BARAT,DOMPU";
        c_array[20] = "KUTACANE,SINABANG,SUBULUSSALAM,LHOKSEUMAWE,SINGKIL,CALANG,LHOKSUKON,IDI RAYEUK,LANGSA,JANTHO,BIREUEN,SIGLI,BANDA ACEH,KUALA SIMPANG,NAGAN RAYA,TAKENGON,MEUREUDU,SIMPANG TIGA REDELONG,MEULABOH,ACEH SELATAN,GAYO LUES,ACEH BARAT DAYA,SABANG";
        c_array[19] = "SANANA,PULAU MOROTAI,HALMAHERA UTARA,TERNATE,HALMAHERA SELATAN,TIDORE,HALMAHERA TIMUR,HALMAHERA BARAT,KEPULAUAN TALIABU,HALMAHERA TENGAH";
        c_array[18] = "SERAM BAGIAN TIMUR,SERAM BAGIAN BARAT,KEPULAUAN ARU,MALUKU TENGGARA,AMBON,BURU,MALUKU TENGAH,BURU SELATAN,MALUKU TENGGARA BARAT,MALUKU BARAT DAYA,TUAL";
        c_array[17] = "LAMPUNG TIMUR,BANDAR LAMPUNG,METRO,WAY KANAN,TULANG BAWANG,TULANG BAWANG BARAT,PESAWARAN,LAMPUNG UTARA,PESISIR BARAT,LAMPUNG TENGAH,TANGGAMUS,PRINGSEWU,LAMPUNG SELATAN,LAMPUNG BARAT,MESUJI";
        c_array[16] = "BINTAN,ANAMBAS,BATAM,LINGGA,KARIMUN,NATUNA,KUATAN SINGINGI,TANJUNG PINANG";
        c_array[15] = "NUNUKAN,MALINAU,TANA TIDUNG,TARAKAN,BULUNGAN";
        c_array[14] = "SAMARINDA,PASER,KUTAI TIMUR,PALARAN,TENGGARONG,BONTANG,BERAU,BALIKPAPAN,SENDAWAR,PENAJAM,MUARA BADAK";
        c_array[13] = "KUALA PEMBUANG,KUALA KAPUAS,KOTAWARINGIN TIMUR,PANGKALANBUN,PULANG PISAU,SUKAMARA,LAMANDAU,KUALA KURUN,PALANGKARAYA,KATINGAN,MURUNG RAYA,TAMIANG LAYANG,MUARA TEWEH,BUNTOK";
        c_array[12] = "HULU SUNGAI TENGAH,BANJARMASIN,KOTABARU,BANJARBARU,TABALONG,TANAH BUMBU,BALANGAN,TANAH LAUT,MARABAHAN,AMUNTAI,RANTAU(BANJARMASIN),KANDANGAN,MARTAPURA(BANJARMASIN)";
        c_array[11] = "MEMPAWAH,BENGKAYANG,SINTANG,SANGGAU,KAPUAS HULU,MELAWI,NGABANG,SEKADAU,SAMBAS,KETAPANG,KUBURAYA,TUMBANG TITI,SINGKAWANG,PONTIANAK,KAYONG UTARA";
        c_array[10] = "BANYUWANGI,SIDOARJO,NGANJUK,MOJOKERTO,WLINGI,TRENGGALEK KOTA,TUBAN,TULUNG AGUNG,MALANG,SAMPANG,GRESIK,MOJOSARI,LUMAJANG,LAMONGAN,BOJONEGORO,BONDOWOSO,BANGKALAN,MAGETAN,JOMBANG,JEMBER,PROBOLINGGO,SUMENEP,KEDIRI,PONOROGO,PAITON,PAMEKASAN,NGASEM,NGAWI,PACITAN,PANDAAN,KEPANJEN,PASURUAN,SITUBONDO,SURABAYA,CARUBAN,BLITAR,MADIUN,BATU";
        c_array[9] = "TEMANGGUNG,SEMARANG,AMBARAWA,UNGARAN,WONOGIRI,WONOSOBO,TEGAL,PURBALINGGA,REMBANG,KEBUMEN,BOYOLALI,MUNGKID,BATANG,SLAWI,BREBES,SRAGEN,SOLO,GROBOGAN,CILACAP,SALATIGA,SUKOHARJO,DEMAK,JEPARA,KARANG ANYAR,KLATEN,KUDUS,KAJEN PEKALONGAN,KENDAL,BANJARNEGARA,CEPU,PEMALANG,PEKALONGAN,PATI,BANYUMAS,PURWOREJO,BLORA,MAGELANG";
        c_array[8] = "SOREANG,CIANJUR,PELABUHAN RATU,KARAWANG,KUNINGAN,BANDUNG,SUMBER,SUKABUMI,TASIKMALAYA,GARUT,CIAMIS,PANGANDARAN,INDRAMAYU,SINGAPARNA,DEPOK,SUBANG,PURWAKARTA,CIREBON,CIMAHI,SUMEDANG,MAJALENGKA,BEKASI,CIKARANG,CIKAMPEK,CIBINONG,BANJAR,NGAMPRAH,BOGOR";
        c_array[7] = "BANGKO,TANJUNG JABUNG BARAT,JAMBI,TANJUNG JABUNG TIMUR,MUARA BUNGO,KERINCI,BATANG HARI,TEBO,SUNGAI PENUH,MUARO JAMBI,SAROLANGUN";
        c_array[6] = "MARISA,TILAMUTA,SUWAWA,GORONTALO,GORONTALO UTARA,KAB.GORONTALO";
        c_array[5] = "JAKARTA";
        c_array[4] = "KULON PROGO,BANTUL,SLEMAN,WONOSARI,YOGYAKARTA";
        c_array[3] = "MUKO-MUKO,KAUR,LEBONG,REJANG LEBONG,KEPAHIANG,ARGAMAKMUR,LUBUK LINGGAU,SELUMA,BENGKULU,BENGKULU TENGAH,MANNA";
        c_array[2] = "KAB.SERANG,CILEGON,TANGERANG,BALARAJA,LEBAK,PANDEGLANG,SERANG,CIPUTAT";
        c_array[1] = "BANGKA SELATAN,BANGKA,PANGKAL PINANG,BELITUNG,BANGKA BARAT,BANGKA TENGAH,BELITUNG TIMUR";
        c_array[0] = "DENPASAR,KLUNGKUNG,BADUNG,TABANAN,BULELENG,BANGLI,KARANG ASEM,JEMBRANA,GIANYAR";

        List sql_list = Lists.newArrayList();

        for (int i = 0; i < p_array.length; i++) {
            int p_id = i + 1;
            String p_sql = String.format(t, p_id, 0, p_id, 1, p_array[i]);
            sql_list.add(p_sql);
            List<String> c_list = Splitter.on(",").trimResults().splitToList(c_array[i]);
            for (int j = 0; j < c_list.size(); j++) {
                int c_id = p_id * 100 + j;
                String c_sql = String.format(t, c_id, p_id, p_id + "," + c_id, 2, c_list.get(j));
                sql_list.add(c_sql);
            }
        }

        IOUtils.writeLines(sql_list, IOUtils.LINE_SEPARATOR_WINDOWS, new FileOutputStream("C:/temp/印尼.sql"), "UTF-8");
    }

}
