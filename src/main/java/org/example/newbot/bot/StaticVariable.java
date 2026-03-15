package org.example.newbot.bot;

import org.apache.commons.lang3.StringUtils;
import org.example.newbot.model.*;
import org.springframework.data.domain.Page;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class StaticVariable {
    public static String[] menu = {
            "➕ \uD835\uDDEC\uD835\uDDD4\uD835\uDDE1\uD835\uDDDA\uD835\uDDDC \uD835\uDDD5\uD835\uDDE2\uD835\uDDE7 \uD835\uDDE2\uD835\uDDD6\uD835\uDDDB\uD835\uDDDC\uD835\uDDE6\uD835\uDDDB",
            "⚙️ \uD835\uDDE6\uD835\uDDE2\uD835\uDDED\uD835\uDDDF\uD835\uDDD4\uD835\uDDE0\uD835\uDDD4",
            "⏳ \uD835\uDDE7\uD835\uDDE2‘\uD835\uDDDF\uD835\uDDE2\uD835\uDDE9\uD835\uDDDF\uD835\uDDD4\uD835\uDDE5",
            "\uD83D\uDCB8 \uD835\uDDDB\uD835\uDDDC\uD835\uDDE6\uD835\uDDE2\uD835\uDDD5\uD835\uDDDC\uD835\uDDE0",
            "\uD83D\uDCE8 \uD835\uDDEC\uD835\uDDE2\uD835\uDDE5\uD835\uDDD7\uD835\uDDD4\uD835\uDDE0"
    };
    public static String backButton = "🔙 Orqaga";
    public static String backButtonRu = "\uD83D\uDD19 Назад";
    public static String mainMenu = "\uD83C\uDFE0 Asosiy menyu";
    public static String mainMenuRu = "\uD83C\uDFE0 Главное меню";
    public static final String addCategory = "➕ Kategoriya qo'shish";
    public static final String addChannel = "➕ Kanal qo'shish";
    public static final String addProduct = "🛒 Mahsulot qo'shish";
    public static final String adminTelegramProfile = "@Ziyatov_Zafar_Official";


    public static String aboutBotPrice(BotPrice botPrice, Boolean isAdmin,double balance) {
        String formattedPrice = formatPrice(botPrice.getPrice());
        StringBuilder response = new StringBuilder();
        if (!isAdmin) {
            response.append("<b>🌟 Bot Narxi:</b> ").append(formattedPrice)
                    .append("\n<b>💡 Tur:</b> ").append(botPrice.getTypeText()).append("\n")
                    .append("<b>📝 Vazifasi:</b> ").append(botPrice.getDescription()).append("\n\n")
                    .append("<b>💰 Sizning hisobingiz: </b> ").append(formatPrice(balance));

        } else {
            response.append("<b>🌟 Bot Narxi:</b> ").append(formattedPrice).append(" So'm\n")
                    .append("<b>💡 Tur: </b> ").append(botPrice.getType()).append("\n")
                    .append("<b>💡 Tur text: </b> ").append(botPrice.getTypeText()).append("\n")
                    .append("<b>📝 Vazifasi:</b> ").append(botPrice.getDescription()).append("\n\n");
        }
        return response.toString();
    }

    public static String[] adminOnlineMagazineMenu = {
            "\uD83D\uDC65 Foydalanuvchilar", "\uD83D\uDCE6 Buyurtmalar", "🛍 Mahsulotlar",
            "📊 Statistika", "📨 Xabar yuborish", "🏢 Filiallar bo'limi"
    };
    public static String[] adminOnlineMagazineBranchMenu = {
            "📋 Barcha filiallar ro'yxati",
            "➕ Filial qo'shish", backButton, mainMenu
    };


    public static String[] adminOnlineMagazineUsersPage = {

            "📋 Barcha mijozlar ro‘yxati",               // Foydalanuvchilarning to‘liq ro‘yxati
            "🚫 Bloklangan foydalanuvchilar",            // Bloklangan foydalanuvchilar ro‘yxati
            "🔎 Foydalanuvchilarni qidirish",               // Username,Nickname,PhoneNumber bo‘yicha izlash
            "🆔 ID orqali qidirish",                     // Unikal ID orqali qidirish
            backButton,                                  // 🔙 Ortga qaytish tugmasi
            mainMenu                                     // 🏠 Asosiy menyuga qaytish
    };

    public static String adminOnlineMagazineAboutUsers(Page<BotUser> botUsers, String botUsername, int page, int size) {
        if (botUsers == null || botUsers.isEmpty()) {
            return "⚠️ Foydalanuvchilar topilmadi";
        }

        StringBuilder message = new StringBuilder();
        message.append("🤖 Bot: @").append(botUsername).append("\n\n");
        message.append("📋 *BARCHA FOYDALANUVCHILAR*\n\n");

        final int MAX_LENGTH = 3500;

        for (int i = 0; i < botUsers.getContent().size(); i++) {
            BotUser user = botUsers.getContent().get(i);

            // Statusni aniqlash
            String status = getStatus(user);

            String userInfo = String.format(
                    """
                            %d. 👤 %s
                               🆔 ID: `<code>%d</code>`
                               💬 Chat ID: `<code>%d</code>`
                               🏷 Nickname: %s
                               📞 Tel: %s
                               📞 Asosiy tel: %s
                               🔗 Username: %s
                               📌 Status: %s
                            
                            """,
                    i + 1,
                    user.getNickname() != null ?
                            String.format("<a href=\"tg://user?id=%d\">%s</a>",
                                    user.getChatId(),
                                    user.getNickname()) :
                            "Noma'lum",
                    user.getId(),
                    user.getChatId(),
                    user.getNickname() != null ? user.getNickname() : "Yo'q",
                    user.getPhone() != null ? user.getPhone() : "Ko'rsatilmagan",
                    user.getHelperPhone() != null ? user.getHelperPhone() : "Ko'rsatilmagan",
                    user.getUsername() != null ?
                            String.format("<a href=\"https://t.me/%s\">@%s</a>", user.getUsername(), user.getUsername()) :
                            "Yo'q",
                    status
            );

            if (message.length() + userInfo.length() > MAX_LENGTH) {
                message.append("\n...va yana ").append(botUsers.getTotalElements() - (i + 1)).append(" ta foydalanuvchi");
                break;
            }
            message.append(userInfo);
        }

        // Sahifalash ma'lumoti
        message.append("\n📌 Sahifa: ").append(page + 1)
                .append("/").append(botUsers.getTotalPages())
                .append(" | Har sahifada: ").append(size).append(" ta")
                .append(" | Jami: ").append(botUsers.getTotalElements()).append(" ta");

        return message.toString();
    }

    public static String adminOnlineMagazineAboutUser(BotUser user) {
        return """
                   👤 *Foydalanuvchi ma'lumotlari*:
                
                
                   👤 %s
                   🆔 ID: `<code>%d</code>`
                   💬 Chat ID: `<code>%d</code>`
                   🏷 Nickname: %s
                   📞 Tel: %s
                   📞 Asosiy tel: %s
                   🔗 Username: %s
                   📌 Status: %s
                
                """.formatted(
                user.getNickname() != null ?
                        String.format("<a href=\"tg://user?id=%d\">%s</a>",
                                user.getChatId(),
                                user.getNickname()) :
                        "Noma'lum",
                user.getId(),
                user.getChatId(),
                user.getNickname() != null ? user.getNickname() : "Yo'q",
                user.getPhone() != null ? user.getPhone() : "Ko'rsatilmagan",
                user.getHelperPhone() != null ? user.getHelperPhone() : "Ko'rsatilmagan",
                user.getUsername() != null ?
                        String.format("<a href=\"https://t.me/%s\">@%s</a>", user.getUsername(), user.getUsername()) :
                        "Yo'q", getStatus(user)

        );
    }

    private static String getStatus(BotUser user) {
        String status;
        if (user.getRole().equals("block")) {
            status = "🚫 Bloklangan";
        } else if ("admin".equalsIgnoreCase(user.getRole())) {
            status = "👑 Admin";
        } else {
            status = "✅ Aktiv";
        }
        return status;
    }

    public static String[] botMakerAdminMenu = new String[]{
            "👥 Foydalanuvchilar", "🤖 Botlar",
            "📊 Statistika", "⚙️ Sozlamalar",
            "📡 Kanallar" , "\uD835\uDDE5\uD835\uDDF2\uD835\uDDF8\uD835\uDDF9\uD835\uDDEE\uD835\uDDFA\uD835\uDDEE"
    };
    public static String[] settingsMenu = new String[]{
            "🤖 Bot sozlamalar",
            "💳 Karta sozlamalar",
            backButton,
            mainMenu
    };
    public static String[] userPageMenu = new String[]{
            "👥 Barcha foydalanuvchilar", "🔍 Qidirish",
            backButton, mainMenu
    };
    public static String[] botMakerAdminBotMenu = new String[]{
            "📋 Barcha botlar",
            "🔍 Qidirish",
            "➕ Bot qo'shish",
            backButton,
            mainMenu
    };
    public static String[] chooseTypeBotList = new String[]{
            "✅ Aktiv botlar",
            "⏸️ To'xtatilgan botlar",
            backButton,
            mainMenu
    };

    public static String aboutCategory(boolean admin, int productsCount, Category category) {
        if (admin) {
            return """
                     <b>📁 Kategoriya ma'lumotlari (Admin)</b>
                                                                       \s
                      🇺🇿 <b>O'zbekcha nomi:</b> %s
                      🇷🇺 <b>Ruscha nomi:</b> %s
                      📦 <b>Mahsulotlar soni:</b> %d
                    """.formatted(
                    category.getNameUz(), category.getNameRu(), productsCount
            );
        } else {
            return """
                    <b>📋 Kategoriyalar ro'yxati</b>
                                                                                \s
                     Quyidagi bo'limlardan birini tanlang:
                     👉 Kerakli kategoriyani bosing""";
        }
    }

    public static String aboutCategoryWithPhoto(boolean admin, Product product, double price, ProductVariant type, String lang) {
        if (admin) {
            return """
                    🏷️ Mahsulot nomi(uz): %s
                    🏷️ Mahsulot nomi(ru): %s
                    📝 Mahsulot tavsifi(uz): %s
                    📝 Mahsulot tavsifi(ru): %s
                    🏷️ Mahsulot turi(uz): %s
                    🏷️ Mahsulot turi(ru): %s
                    💰 Mahsulot narxi: %s
                    """.formatted(
                    product.getNameUz(),
                    product.getNameRu(),
                    product.getDescriptionUz(),
                    product.getDescriptionRu(),
                    type.getNameUz(),
                    type.getNameRu(),
                    formatPrice(price)
            );

        } else {
            {
                if (lang.equals("uz")) {
                    return """
                            %s %s
                            
                            Mahsulot narxi: %s""".formatted(product.getNameUz(), product.getDescriptionUz(), formatPrice(price));
                } else return """
                        %s %s
                        
                        Цена продукта: %s""".formatted(product.getNameRu(), product.getDescriptionRu(), formatPrice(price));

            }
        }
    }

    public static String formatPrice(double price) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("uz", "UZ"));
        nf.setMaximumFractionDigits(2);
        return nf.format(price) + " so‘m";
    }

    public static String UsersStatisticsForOnlineMagazine(List<BotUser> list, Long adminChatId, String botUsername) {
        int admins = 0, users = 0, blocks = 0;
        boolean success = false;
        for (BotUser user : list) {
            if (!success) {
                if (adminChatId.equals(user.getChatId())) {
                    success = true;
                }
            }
            switch (user.getRole()) {
                case "admin" -> admins++;
                case "user" -> users++;
                case "block" -> blocks++;
            }
        }
        long userSize = list.size();
        return """
                📊 @%s Statistikasi
                
                👥 Umumiy foydalanuvchilar: %d ta
                🚫 Bloklangan foydalanuvchilar: %d ta
                ✅ Faol foydalanuvchilar: %d ta
                🛠️ Adminlar: %d ta
                """.formatted(botUsername, userSize, blocks, users, admins);
    }

    public static String[] isSuccessForText(String lang) {
        return lang.equals("uz")
                ? new String[]{"✅ Ha", "❌ Yo‘q"}
                : new String[]{"✅ Да", "❌ Нет"};
    }

}
