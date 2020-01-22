package com.eduapps.edumage.oge_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import com.eduapps.edumage.oge_app.data.Tables;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import static java.lang.Math.min;

public class DbHelper extends SQLiteAssetHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "tasks.db";
    private Context context;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        //super.setForcedUpgrade();
    }

    @Override
    public synchronized SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion > 1) {
            db.execSQL("UPDATE UseOfEnglish SET answer='AREMADE' WHERE _id=726");
            db.execSQL("UPDATE UseOfEnglish SET answer='ISIMPROVING' WHERE _id=449");
            db.execSQL("UPDATE UseOfEnglish SET answer='GEOGRAPHIC/GEOGRAPHICAL' WHERE _id=754");
            db.execSQL("UPDATE UseOfEnglish SET task='\"Give it to me anyway,\" Bella said. \"If " +
                    "I don’t find a proper pen, I __________________ in green ink. I hope it won’t " +
                    "affect my grades.\"' WHERE _id=402");
            db.execSQL("UPDATE UseOfEnglish SET task='My mum calls me Snow White. It was " +
                    "my __________________  nickname but it stuck to me forever.' WHERE _id=92");
            db.execSQL("UPDATE Writing SET task='Thank you for your recent letter. I was glad to hear from you again.\n" +
                    "Sasha\n" +
                    "May 24th, 2019\n" +
                    "In your letter you asked me about my future job. Well, I’d like to become a programmer as I believe it’s one of the most important professions today. Besides learning how to code, I will need to use English a lot because with the help of English I can get access to information for programmers written in English. Also, I will be able to cooperate with people from all over the world. However, my parents don’t agree with my choice. They advise me to be an accountant as they believe it brings much more money.\n" +
                    "Hope to hear from you soon.\n" +
                    "Moscow, Russia\n" +
                    "Dear Ben,\n" +
                    "Best wishes,' WHERE _id=7");
            db.execSQL("UPDATE Writing SET answer='Moscow, Russia\n" +
                    "May 24th, 2019\n" +
                    "Dear Ben,\n" +
                    "Thank you for your recent letter. I was glad to hear from you again.\n" +
                    "In your letter you asked me about my future job. Well, I’d like to become a programmer as I believe it’s one of the most important professions today. Besides learning how to code, I will need to use English a lot because with the help of English I can get access to information for programmers written in English. Also, I will be able to cooperate with people from all over the world. However, my parents don’t agree with my choice. They advise me to be an accountant as they believe it brings much more money.\n" +
                    "Hope to hear from you soon.\n" +
                    "Best wishes,\n" +
                    "Sasha' WHERE _id=7");

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("Dont_show_uoe", false);
            editor.apply();
        }
        if (newVersion == 3) {
            // удаляем старые задания на чтение из 2019 года
            // стираем прогресс
            int completedTasks = 0;

            String[] columns = new String[]{Tables.ReadingTask1.COLUMN_COMPLETION};
            Cursor cursor = db.query(Tables.ReadingTask1.TABLE_NAME, columns, null,
                    null, null, null, null);
            int completionColumnIndex = cursor.getColumnIndex(Tables.ReadingTask1.COLUMN_COMPLETION);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                if (cursor.getInt(completionColumnIndex) == 100) {
                    completedTasks++;
                }
            }
            cursor.close();

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            if (preferences.contains("ReadingFullCompletion")) {
                int full = preferences.getInt("ReadingFullCompletion", 0);
                editor.putInt("ReadingFullCompletion", min(0, full - completedTasks));
                editor.apply();
            }

            // обновляем задания

            String text1 = "'Tower Bridge, which is over a hundred years old, has become a symbol of London. It is the only bridge on the Thames that can be raised and lowered to allow ships to pass. Nowadays it takes only 90 seconds for the heavy drawbridges to be pulled up with electric motors. It is considered that watching the Tower Bridge opening brings good luck.\n" +
                    "Waterloo Bridge is a foot traffic bridge crossing the River Thames in London. It was opened in 1817, on the second anniversary of the famous battle. A century later, in the early 1940s, the famous Bridge needed to be rebuilt. It was during World War II, and most men were away fighting. So the bridge was rebuilt mainly by women. The new Waterloo Bridge was opened in 1945 and got a second name, the ’Ladies Bridge’.\n" +
                    "Wembley Stadium is a football stadium located in Wembley Park, London. The stadium is home not only to football. It also hosts concerts, rugby games and American football games. There is Wembley Market not far from the stadium. Unlike many London street markets, this one is situated in an open space. A visit here is a good option for football fans to find club T-shirts, boots or accessories.\n" +
                    "23 and 24 Leinster Gardens in Paddington (just opposite Hyde Park) are fake houses built to hide the Tube line running underneath. The windows are painted on, there are no letter boxes, and behind the facade there is a railway. The first London underground trains were steam trains so they needed ventilation. Underground lines were planned with tunnels and open-air sections so the trains could let out their steam and smoke, and that is what the house facades are hiding.\n" +
                    "Great Ormond Street Hospital, which is situated at Russell Square, London, owns the copyright to Peter Pan, a story written by J.M. Barrie. The author had no children himself and gifted the rights to his famous literary pieces to the hospital in 1929. The hospital receives royalties from all films, cartoons and performances of Peter Pan. All the money is used to run the hospital.\n" +
                    "Cleopatra’s Needle was brought to London in 1819 from Alexandria, the royal city of Cleopatra. Underneath Cleopatra’s Needle there’s a time capsule from 1778. It keeps information about 18th century life. It contains copies of the Bible in several languages, a portrait of Queen Victoria, a set of British coins, cigars, a razor, a map of London, copies of 10 daily newspapers and pictures of the 12 best-looking English women of the day.'";
            String task1 = "'Выберите вопрос\n" +
                    "1. Which place in London keeps a message for future generations?\n" +
                    "2. Which place in London is good to watch sports and buy related goods?\n" +
                    "3. How long does it take to raise the famous bridge for a ship?\n" +
                    "4. How does a fairytale character help real people of London?\n" +
                    "5. Which London bridge got a nickname after its reconstruction?\n" +
                    "6. What is the most visited tourist attraction in London?\n" +
                    "7. What were the false buildings in London made for?'";
            String answer1 = "'3 5 2 7 4 1'";
            String explanation1 = "'A. Tower Bridge, which is over a hundred years old, has become a symbol of London. It is the only bridge on the Thames that can be raised and lowered to allow ships to pass. |Nowadays it takes only 90 seconds for the heavy drawbridges to be pulled up with electric motors|. It is considered that watching the Tower Bridge opening brings good luck.\n" +
                    "---\n" +
                    "B. Waterloo Bridge is a foot traffic bridge crossing the River Thames in London. It was opened in 1817, on the second anniversary of the famous battle. A century later, in the early 1940s, the famous Bridge needed to be rebuilt. It was during World War II, and most men were away fighting. |So the bridge was rebuilt mainly by women. The new Waterloo Bridge was opened in 1945 and got a second name, the ’Ladies Bridge’|.\n" +
                    "---\n" +
                    "C. Wembley Stadium is |a football stadium| located in Wembley Park, London. The stadium is home not only to football. It also hosts concerts, rugby games and American football games. There is Wembley Market not far from the stadium. Unlike many London street markets, this one is situated in an open space. |A visit here is a good option for football fans to find club T-shirts, boots or accessories|.\n" +
                    "---\n" +
                    "D. 23 and 24 Leinster Gardens in Paddington (just opposite Hyde Park) are |fake houses built to hide the Tube line running underneath|. The windows are painted on, there are no letter boxes, and behind the facade there is a railway. The first London underground trains were steam trains so they needed ventilation. Underground lines were planned with tunnels and open-air sections so the trains could let out their steam and smoke, and that is what the house facades are hiding.\n" +
                    "---\n" +
                    "E. Great Ormond Street Hospital, which is situated at Russell Square, London, owns the copyright to |Peter Pan, a story written by J.M. Barrie|. The author had no children himself and gifted the rights to his famous literary pieces to the hospital in 1929. |The hospital receives royalties from all films, cartoons and performances of Peter Pan. All the money is used to run the hospital|.\n" +
                    "---\n" +
                    "F. Cleopatra’s Needle was brought to London in 1819 from Alexandria, the royal city of Cleopatra. |Underneath Cleopatra’s Needle there’s a time capsule from 1778. It keeps information about 18th century life|. It contains copies of the Bible in several languages, a portrait of Queen Victoria, a set of British coins, cigars, a razor, a map of London, copies of 10 daily newspapers and pictures of the 12 best-looking English women of the day.'";

            String text2 = "'The Fab Four, the famous Liverpool musical group commonly known as The Beatles, reached the peak of their popularity in the 1960s. But the real story began much earlier when Paul McCartney heard the skiffle country music played by John Lennon’s group at St. Paul’s church in Liverpool. McCartney was a brilliant guitarist, so they let him join the group at once.\n" +
                    "The group changed its name several times, from Johnny and the Moondogs to Long John and the Silver Beatles and finally The Beatles. The short title is in itself a smart mixture of two words. The beat is a synonym for musical rhythm, while the beetles describe insects producing sounds using their shiny wings.\n" +
                    "Lennon, Harrison, McCartney and Starr were the heart of The Beatles while the other members constantly changed. Many guitarists and drummers joined but finally left the group. The style changed too. When The Beatles left Britain to perform in Europe, they turned from folk to rock-n-roll to win over the public. They also played foreign music and composed their own songs.\n" +
                    "Soon the group issued their first single, My Bonnie, which became popular in Liverpool. A customer once asked for it in a music shop, but there were no copies left. When Epstein, the shop owner, finally got the disc, he was surprised to hear a good quality song by the group that played in the club next to his shop. The same month he signed the first contract with The Beatles as their musical agent.\n" +
                    "Although in 1963 the group issued more than three hundred thousand copies of their second album at home, they only broke the American market a year later. During their first tour to the USA the group was welcomed by thousands of fans at Kennedy airport in New York. For half a year The Beatles were touring the USA and were top of the charts.\n" +
                    "During their USA tour The Beatles visited Elvis Presley. They talked a lot, played music and managed to record several priceless compositions. The reason why none of the songs have been issued is widely discussed today. No matter how hard musical agents tried to find the recordings, they failed. This is one of the secrets The Beatles left unrevealed.'";
            String task2 = "'Выберите вопрос\n" +
                    "1. How did the band get their first manager?\n" +
                    "2. How many songs did The Beatles record?\n" +
                    "3. What does the band’s name mean?\n" +
                    "4. What music of the band was lost forever?\n" +
                    "5. How did The Beatles start?\n" +
                    "6. What sort of music did The Beatles play? \n" +
                    "7. How did The Beatles conquer America?'";
            String answer2 = "'5 3 6 1 7 4'";
            String explanation2 = "'A. The Fab Four, the famous Liverpool musical group commonly known as The Beatles, reached the peak of their popularity in the 1960s. But the real story began much earlier when |Paul McCartney heard the skiffle country music played by John Lennon’s group at St. Paul’s church in Liverpool. McCartney was a brilliant guitarist, so they let him join the group at once|.\n" +
                    "---\n" +
                    "B. The group changed its name several times, from Johnny and the Moondogs to Long John and the Silver Beatles and finally The Beatles. |The short title is in itself a smart mixture of two words. The beat is a synonym for musical rhythm, while the beetles describe insects producing sounds using their shiny wings|.\n" +
                    "---\n" +
                    "C. Lennon, Harrison, McCartney and Starr were the heart of The Beatles while the other members constantly changed. Many guitarists and drummers joined but finally left the group. The style changed too. When The Beatles left Britain to perform in Europe, |they turned from folk to rock-n-roll to win over the public. They also played foreign music and composed their own songs|.\n" +
                    "---\n" +
                    "D. Soon the group issued their first single, My Bonnie, which became popular in Liverpool. |A customer once asked for it in a music shop, but there were no copies left. When Epstein, the shop owner, finally got the disc, he was surprised to hear a good quality song by the group that played in the club next to his shop. The same month he signed the first contract with The Beatles as their musical agent|.\n" +
                    "---\n" +
                    "E. Although in 1963 the group issued more than three hundred thousand copies of their second album at home, they only broke the American market a year later. |During their first tour to the USA the group was welcomed by thousands of fans at Kennedy airport in New York. For half a year The Beatles were touring the USA and were top of the charts|.\n" +
                    "---\n" +
                    "F. During their USA tour The Beatles visited Elvis Presley. They talked a lot, played music and managed to record several priceless compositions. |The reason why none of the songs have been issued is widely discussed today. No matter how hard musical agents tried to find the recordings, they failed|. This is one of the secrets The Beatles left unrevealed.'";

            String text3 = "'Somewhere in a far-away magical kingdom, there lived a beautiful princess, a prince and animals who could talk like humans. This is a typical plot for many fairy tales. In 1990, a short story for children about Shrek, a big green troll, appeared. Unlike previous heroes he looked ugly and scary but had a big, kind heart. In 2001, DreamWorks studio made the first animated Shrek cartoon.\n" +
                    "All the characters of the cartoons about Shrek are easy to remember and have a good sense of humour. The cartoons are full of episodes about friendship, good and evil, love and family values. The cartoons present an amusing mixture of traditional fairy tales and real-life events familiar to everyone. All that made the story of the troll very popular, especially with children.\n" +
                    "Maurice Tillet was a famous professional French boxer and wrestler of the 20th century. Unfortunately, at the age of twenty he got a rare disease which changed his body and appearance. It made him look like a huge troll. Few people know that Shrek’s appearance copies that of Maurice Tillet. However, in contrast to the cartoon character, Maurice Tillet was highly intelligent – he spoke fourteen languages and was good at writing prose.\n" +
                    "Several scenes in cartoons about Shrek are based on scenes from famous movies. For example, the ’Welcome to Duloc’ song sounds like the popular Disney tune ’It’s a Small World’. During the fight between Fiona and Robin Hood’s men, the camera moves as in ’The Matrix’. There are hints of the ’The Lord of the Rings’ and ’Mission Impossible’.\n" +
                    "The famous story was also made into a Broadway show, ’Shrek the Musical’. Technically, the musical show is not the same as the film but it has its advantages. Children and adults enjoy seeing celebrities in the roles of Shrek and Princess Fiona. The success of the musical is also based on a nice combination of new and old popular songs.\n" +
                    "The stories about the green troll and his friends are for all ages. Teenagers and grown-ups may enjoy modern computer games about Shrek. Computer stories have attractive design and dynamic plots, so gamers and fans will not be bored. Though the images of Shrek and Fiona are a bit different from the film, they are easily recognizable.'";
            String task3 = "'Выберите вопрос\n" +
                    "1. Why is the musical about Shrek successful?\n" +
                    "2. What makes Shrek different from traditional fairytale characters?\n" +
                    "3. The voices of which celebrities can we hear in the cartoons about Shrek?\n" +
                    "4. What famous films do the cartoons about Shrek remind us of?\n" +
                    "5. Why do children like the cartoons about Shrek?\n" +
                    "6. Why can Shrek’s story be interesting to computer gamers? \n" +
                    "7. What person does Shrek look like?'";
            String answer3 = "'2 5 7 4 1 6'";
            String explanation3 = "'A. Somewhere in a far-away magical kingdom, there lived a beautiful princess, a prince and animals who could talk like humans. This is a typical plot for many fairy tales. In 1990, a short story for children about Shrek, a big green troll, appeared. |Unlike previous heroes he looked ugly and scary but had a big, kind heart|. In 2001, DreamWorks studio made the first animated Shrek cartoon.\n" +
                    "---\n" +
                    "B. All the characters of the cartoons about Shrek are easy to remember and have a good sense of humour. The cartoons are full of episodes about friendship, good and evil, love and family values. The cartoons present an amusing mixture of traditional fairy tales and real-life events familiar to everyone. |All that made the story of the troll very popular, especially with children|.\n" +
                    "---\n" +
                    "C. Maurice Tillet was a famous professional French boxer and wrestler of the 20th century. Unfortunately, at the age of twenty he got a rare disease which changed his body and appearance. It made him look like a huge troll. Few people know that |Shrek’s appearance copies that of Maurice Tillet|. However, in contrast to the cartoon character, Maurice Tillet was highly intelligent – he spoke fourteen languages and was good at writing prose.\n" +
                    "---\n" +
                    "D. Several scenes in cartoons about Shrek are based on scenes from famous movies. For example, |the ’Welcome to Duloc’ song sounds like the popular Disney tune ’It’s a Small World’. During the fight between Fiona and Robin Hood’s men, the camera moves as in ’The Matrix’. There are hints of the ’The Lord of the Rings’ and ’Mission Impossible’|.\n" +
                    "---\n" +
                    "E. The famous story was also made into a Broadway show, ’Shrek the Musical’. Technically, the musical show is not the same as the film but it has its advantages. |Children and adults enjoy seeing celebrities in the roles of Shrek and Princess Fiona. The success of the musical is also based on a nice combination of new and old popular songs|.\n" +
                    "---\n" +
                    "F. The stories about the green troll and his friends are for all ages. Teenagers and grown-ups may enjoy modern computer games about Shrek. |Computer stories have attractive design and dynamic plots, so gamers and fans will not be bored|. Though the images of Shrek and Fiona are a bit different from the film, they are easily recognizable.'";

            String text4 = "'For people who live in the northern hemisphere, the thought of celebrating Christmas with great heat and bright sun seems strange and exotic. But in Australia, where the holiday falls in summer, it’s completely normal. Though most of the traditions of celebrating Christmas in Australia are the same as in European countries, there is still a little variation. On Christmas Day, most Australians have the holiday midday dinner outside. The dinner is often followed by some backyard cricket or a swim in the pool.\n" +
                    "In Italy, children do not ask Santa Claus for presents. In their country an ugly, yet kind old witch named Befana performs Santa’s duties. The witch flies around the world on her broomstick and enters the houses down the chimney. She delivers toys, clothing and candy to well-behaved children and puts coal — or dark candy — in bad kids’ socks.\n" +
                    "A Ukrainian legend tells us about children from a poor family. Unfortunately, their parents could not afford any Christmas decorations and the kids were upset. However, on Christmas morning when the children woke up, they saw that spiders had spun webs of shiny silk around the tree’s branches. The sun turned each thread into silver and gold. Even nowadays the Ukrainians decorate their trees with spider webs to welcome good luck.\n" +
                    "Different celebrations take place over Christmas in Scotland. One of the most important traditions is called First-Footing. Once midnight strikes, all eyes await the arrival of the year’s first visitor. The person who crosses the home’s threshold first is said to bring good fortune for the year ahead. Top of the lucky list: a male, dark-haired visitor. Women or blonde men are believed to be unlucky.\n" +
                    "According to tradition, on Christmas Eve some Russian people don’t eat anything until the first star has appeared in the sky. In Russia, Christmas is celebrated on the 7th of January, not on the 25th of December like in most other countries. The different date of the holiday is because the Orthodox Church uses the old ’Julian’ calendar for religious celebration days.\n" +
                    "Like in most countries the locals of Venezuela, especially in their capital city, Caracas, go to church on Christmas Eve to celebrate the birth of Jesus. However, in Caracas, people of all ages don’t just walk to church, they usually roller skate to church. The streets of Caracas are closed to vehicles up to 8am on Christmas Day to make way for their citizens who use roller skates to travel to and from church.'";
            String task4 = "'Выберите вопрос\n" +
                    "1. What is the most unusual way to get to church on Christmas night?\n" +
                    "2. Who can bring Christmas presents instead of Santa?\n" +
                    "3. Why is the first guest on Christmas Day so important?\n" +
                    "4. What creature helped to decorate a Christmas tree?\n" +
                    "5. What transport does Santa use in warm countries?\n" +
                    "6. How do people celebrate Christmas in hot climate?\n" +
                    "7. Why is Christmas celebrated on another date in this country?'";
            String answer4 = "'6 2 4 3 7 1'";
            String explanation4 = "'A. For people who live in the northern hemisphere, the thought of |celebrating Christmas with great heat and bright sun| seems strange and exotic. But in Australia, where the holiday falls in summer, it’s completely normal. Though most of the traditions of celebrating Christmas in Australia are the same as in European countries, there is still a little variation. On Christmas Day, most Australians have the holiday midday dinner outside. The dinner is often followed by some backyard cricket or a swim in the pool.\n" +
                    "---\n" +
                    "B. In Italy, children do not ask Santa Claus for presents. |In their country an ugly, yet kind old witch named Befana performs Santa’s duties|. The witch flies around the world on her broomstick and enters the houses down the chimney. |She delivers toys, clothing and candy to well-behaved children and puts coal — or dark candy — in bad kids’ socks|.\n" +
                    "---\n" +
                    "C. A Ukrainian legend tells us about children from a poor family.\n" +
                    "Unfortunately, their parents could not afford any Christmas decorations and the kids were upset. |However, on Christmas morning when the children woke up, they saw that spiders had spun webs of shiny silk around the tree’s branches|. The sun turned each thread into silver and gold. Even nowadays the Ukrainians decorate their trees with spider webs to welcome good luck.\n" +
                    "---\n" +
                    "D. Different celebrations take place over Christmas in Scotland. One of the most important traditions is called First-Footing. Once midnight strikes, all eyes await the arrival of the year’s first visitor. |The person who crosses the home’s threshold first is said to bring good fortune for the year ahead|. Top of the lucky list: a male, dark-haired visitor. Women or blonde men are believed to be unlucky.\n" +
                    "---\n" +
                    "E. According to tradition, on Christmas Eve some Russian people don’t eat anything until the first star has appeared in the sky. In Russia, Christmas is celebrated on the 7th of January, not on the 25th of December like in most other countries. |The different date of the holiday is because the Orthodox Church uses the old ’Julian’ calendar for religious celebration days|.\n" +
                    "---\n" +
                    "F. Like in most countries the locals of Venezuela, especially in their capital city, Caracas, go to church on Christmas Eve to celebrate the birth of Jesus. |However, in Caracas, people of all ages don’t just walk to church, they usually roller skate to church|. The streets of Caracas are closed to vehicles up to 8am on Christmas Day to make way for their citizens who use roller skates to travel to and from church.'";

            String text5 = "'There are only three city-states in the world. One of them is Singapore, a small country in South East Asia. It can be called a modern equivalent to the Italian medieval Venice. Both cities are built on islands and became wealthy by trade. Like medieval Venice, Singapore is an independent city-state and is governed by a small group of powerful politicians and businessmen.\n" +
                    "Modern Singapore is full of numerous statues of the king of animals. According to legend, the founder of the city saw a lion on the island and decided that it was a sign of good luck. The Malay word for the country, Singapura, means Lion City. However, zoologists doubt that he could see a lion because lions have never lived on the island. More likely, he saw a tiger or another kind of big cat native to the area.\n" +
                    "From an extremely humid but sunny morning to a gloomy and rainy afternoon, and then to a cloudy, starless night sky; Singapore is predictable in its climate. A week of terrible heat follows a week of non-stop rainfall. In general, tourists think February is the best month to visit the country. The citizens of Singapore disagree. They think that any time is good – you just have to accept the hot and rainy weather.\n" +
                    "Singapore has three main communities. The largest group is the Chinese, then come the Malays and the smallest group is the Indians. English is the language of government, television and universities. Along with English, the official languages of the country are Mandarin, Malay and Tamil. The national anthem Majulah Singapura is actually sung in Malay.\n" +
                    "The island is densely populated and the government has to think about the environment. Unlike in most countries, citizens of Singapore cannot just buy a car. To own a car, a citizen must enter his or her name in a lottery that is drawn twice a month. If the citizen wins the lottery, he or she is granted a Certificate which allows them to own a car for 10 years. Then it is necessary to pay a registration fee, which is 140% of the value of the car.\n" +
                    "When you are in Singapore, you can’t buy chewing gum anywhere. It is illegal to import or sell gum in the country. The sale of gum was prohibited in 1992 after gum was used to shut down the SMRT, the country’s public transportation system. The gum was stuck on the sensor doors and the system was paralyzed. The punishment for bringing gum into the country is a year in prison and a big fine.'";
            String task5 = "'Выберите вопрос\n" +
                    "1. What should you take out of your luggage when you travel to Singapore?\n" +
                    "2. Which city has much in common with Singapore?\n" +
                    "3. What is the coldest month on the island?\n" +
                    "4. How do the people of the island feel about their weather?\n" +
                    "5. What animal gave its name to the city?\n" +
                    "6. How many official languages has the country got?\n" +
                    "7. How does the government limit the number of car owners?'";
            String answer5 = "'2 5 4 6 7 1'";
            String explanation5 = "'A. There are only three city-states in the world. One of them is Singapore, a small country in South East Asia. |It can be called a modern equivalent to the Italian medieval Venice. Both cities are built on islands and became wealthy by trade. Like medieval Venice, Singapore is an independent city-state and is governed by a small group of powerful politicians and businessmen|.\n" +
                    "---\n" +
                    "B. Modern Singapore is full of numerous statues of the king of animals. According to legend, the founder of the city saw a lion on the island and decided that it was a sign of good luck. |The Malay word for the country, Singapura, means Lion City|. However, zoologists doubt that he could see a lion because lions have never lived on the island. More likely, he saw a tiger or another kind of big cat native to the area.\n" +
                    "---\n" +
                    "C. From an extremely humid but sunny morning to a gloomy and rainy afternoon, and then to a cloudy, starless night sky; Singapore is predictable in its climate. A week of terrible heat follows a week of non-stop rainfall. In general, tourists think February is the best month to visit the country. |The citizens of Singapore disagree. They think that any time is good – you just have to accept the hot and rainy weather|.\n" +
                    "---\n" +
                    "D. Singapore has three main communities. The largest group is the Chinese, then come the Malays and the smallest group is the Indians. English is the language of government, television and universities. |Along with English, the official languages of the country are Mandarin, Malay and Tamil|. The national anthem Majulah Singapura is actually sung in Malay.\n" +
                    "---\n" +
                    "E. The island is densely populated and the government has to think about the environment. Unlike in most countries, citizens of Singapore cannot just buy a car. |To own a car, a citizen must enter his or her name in a lottery that is drawn twice a month. If the citizen wins the lottery, he or she is granted a Certificate which allows them to own a car for 10 years. Then it is necessary to pay a registration fee, which is 140% of the value of the car|.\n" +
                    "---\n" +
                    "F. When you are in Singapore, you can’t buy chewing gum anywhere. |It is illegal to import or sell gum in the country|. The sale of gum was prohibited in 1992 after gum was used to shut down the SMRT, the country’s public transportation system. The gum was stuck on the sensor doors and the system was paralyzed. The punishment for bringing gum into the country is a year in prison and a big fine.'";

            String text6 = "'The company that makes the famous little plastic bricks known as LEGO started as a small shop in the town of Billund in Denmark. At first the shop sold wooden toys and other things. Soon the business became known as LEGO. It came from the Danish words ’LEg GOdt’, meaning ’play well’. Later, it was realized that the original meaning in Latin was ’I put together’.\n" +
                    "The LEGO Group was founded in 1932 by Ole Kirk Cristiansen. The company has come a long way from a small carpenter’s workshop to a modern, global corporation, the world’s third-largest producer of toys. Lego has passed from father to son and is now owned by a grandchild of the founder. As a child, he often came up with the ideas for new models and Lego sets.\n" +
                    "The brick, the main component of all Lego sets, appeared in its present form in 1958 and since then has remained compatible with previous editions. This little piece of plastic offers unlimited building possibilities. It lets children experiment and try out their creative ideas. The LEGO company owes its success to the traditional Lego brick. The company has been awarded ’Toy of the Century’ twice.\n" +
                    "Last year Charlotte Benjamin wrote a letter to the Lego Company in which she complained that, during a visit to the toy store, she noticed that ’there are lots of Lego boy people and barely any Lego girls.’ She felt sad that, in Lego, girl figures mostly sat at home, went shopping and had no job. At the same time boy figures went on adventures, worked, saved people and ’even swam with sharks’.\n" +
                    "The LEGO Group produces thousands of sets with a variety of themes. In 1969 the company introduced Lego Duplo, designed for children who are 1 to 5 years old. Duplo bricks are twice the length, height and width of traditional Lego bricks. It makes them easier to handle and less likely to be swallowed by younger children. Duplo sets now include farm, zoo, town, castle and pirate sets.\n" +
                    "Lego Games are a great way of having fun together with family and friends. These sets excite imagination and improve creativity because the child needs to put a game together before he or she can play it. They also develop hand and eye coordination, teach children to follow directions with logic and find scientific and technological solutions. In a fun way, these games promote basic ideas of Maths, Geometry and Engineering.'";
            String task6 = "'Выберите вопрос\n" +
                    "1. Which object made the company famous?\n" +
                    "2. Why are some sets of LEGO twice as big?\n" +
                    "3. Where does the name LEGO come from?\n" +
                    "4. Why is LEGO considered to be an educational toy?\n" +
                    "5. Who are the owners of LEGO?\n" +
                    "6. Why do adults enjoy LEGO toys?\n" +
                    "7. What can make some LEGO-toy customers unhappy?'";
            String answer6 = "'3 5 1 7 2 4'";
            String explanation6 = "'A. The company that makes the famous little plastic bricks known as LEGO started as a small shop in the town of Billund in Denmark. At first the shop sold wooden toys and other things. Soon the business became known as LEGO. |It came from the Danish words ’LEg GOdt’, meaning ’play well’. Later, it was realized that the original meaning in Latin was ’I put together’|.\n" +
                    "---\n" +
                    "B. |The LEGO Group was founded in 1932 by Ole Kirk Cristiansen|. The company has come a long way from a small carpenter’s workshop to a modern, global corporation, the world’s third-largest producer of toys. Lego has passed from father to son and is now owned by a grandchild of the founder. As a child, he often came up with the ideas for new models and Lego sets.\n" +
                    "---\n" +
                    "C. The brick, the main component of all Lego sets, appeared in its present form in 1958 and since then has remained compatible with previous editions. This little piece of plastic offers unlimited building possibilities. It lets children experiment and try out their creative ideas. |The LEGO company owes its success to the traditional Lego brick|. The company has been awarded ’Toy of the Century’ twice.\n" +
                    "---\n" +
                    "D. Last year Charlotte Benjamin wrote a letter to the Lego Company in which |she complained that, during a visit to the toy store, she noticed that ’there are lots of Lego boy people and barely any Lego girls’|. She felt sad that, in Lego, girl figures mostly sat at home, went shopping and had no job. At the same time boy figures went on adventures, worked, saved people and ’even swam with sharks’.\n" +
                    "---\n" +
                    "E. The LEGO Group produces thousands of sets with a variety of themes. In 1969 the company introduced Lego Duplo, designed for children who are 1 to 5 years old. Duplo bricks are twice the length, height and width of traditional Lego bricks. |It makes them easier to handle and less likely to be swallowed by younger children|. Duplo sets now include farm, zoo, town, castle and pirate sets.\n" +
                    "---\n" +
                    "F. Lego Games are a great way of having fun together with family and friends. These sets excite imagination and improve creativity because the child needs to put a game together before he or she can play it. They also develop hand and eye coordination, teach children to follow directions with logic and find scientific and technological solutions. |In a fun way, these games promote basic ideas of Maths, Geometry and Engineering|.'";

            String text7 = "'Chocolate is the most common present on St. Valentine’s Day. The tradition goes back to the 1800s when doctors made patients eat chocolate to make them feel happier. This might also be the reason why in the 1860s, Richard Cadbury produced his heart-shaped box of chocolates exclusively for Valentine’s Day.\n" +
                    "St Valentine’s Day is a perfect occasion to express your deepest feelings to the person you love. On Valentine’s Day lovers, friends and family members exchange Valentine’s Day gifts as symbols of love. However, statistics show that nearly 9 million people prefer celebrating Valentine’s Day with their pets and give them presents. The reason is that most pet owners consider animals to be more grateful and loyal than humans.\n" +
                    "Each rose sent on Valentine’s Day has some meaning. For example, a red rose means love and respect and pink says, ’I am having sweet thoughts about you’. Also, it is believed that one rose stands for love at first sight, eleven flowers mean that the receiver is truly and deeply loved and a hundred and eight roses are recommended for marriage proposals.\n" +
                    "Some superstitions about seeing birds on Valentine’s Day are really funny. It was once believed that if a woman saw a flying robin, she would get married to a sailor. However, if she saw a sparrow, her future husband would be poor. If she saw a goldfinch, she would marry a millionaire. There is no answer to who she would marry if she saw a pigeon, as history does not say anything about pigeons.\n" +
                    "Romeo and Juliet, the two characters from the play by William Shakespeare, are remembered all over the world as an emblem of romance. The young lovers lived in Verona, Italy. Every Valentine’s Day, this city still receives about 1,000 letters addressed to Juliet. The fictional character is still alive for many people who believe in romantic and immortal love.\n" +
                    "Started by a group of feminists, Quirkyalone Day is celebrated on February 14 as an alternative to Valentine’s Day. The new holiday started in 2003 as a celebration of romance, freedom and individuality. It’s a day to celebrate the things you enjoy doing alone. Ways to celebrate include: buying yourself a new dress, taking a long walk without your mobile phone, exploring a new part of town, trying a new recipe etc.'";
            String task7 = "'Выберите вопрос\n" +
                    "1. Why do a lot of letters come to the Italian city on Valentine’s Day? \n" +
                    "2. Why is it important to see the right bird on Valentine’s Day?\n" +
                    "3. What message can be sent with the flowers?\n" +
                    "4. Why do some people give St Valentine’s presents to their pets?\n" +
                    "5. What is a different way to celebrate February 14?\n" +
                    "6. How did the holiday get its name?\n" +
                    "7. Why did a traditional St Valentine’s box of sweets have the form of a heart?'";
            String answer7 = "'7 4 3 2 1 5'";
            String explanation7 = "'A. Chocolate is the most common present on St. Valentine’s Day. The tradition goes back to the 1800s when |doctors made patients eat chocolate to make them feel happier. This might also be the reason why in the 1860s, Richard Cadbury produced his heart-shaped box of chocolates exclusively for Valentine’s Day|.\n" +
                    "---\n" +
                    "B. St Valentine’s Day is a perfect occasion to express your deepest feelings to the person you love. On Valentine’s Day lovers, friends and family members exchange Valentine’s Day gifts as symbols of love. |However, statistics show that nearly 9 million people prefer celebrating Valentine’s Day with their pets and give them presents. The reason is that most pet owners consider animals to be more grateful and loyal than humans|.\n" +
                    "---\n" +
                    "C. Each rose sent on Valentine’s Day has some meaning. For example, |a red rose means love and respect and pink says, ’I am having sweet thoughts about you’. Also, it is believed that one rose stands for love at first sight, eleven flowers mean that the receiver is truly and deeply loved and a hundred and eight roses are recommended for marriage proposals|.\n" +
                    "---\n" +
                    "D. Some superstitions about seeing birds on Valentine’s Day are really funny. It was once believed that |if a woman saw a flying robin, she would get married to a sailor. However, if she saw a sparrow, her future husband would be poor. If she saw a goldfinch, she would marry a millionaire|. There is no answer to who she would marry if she saw a pigeon, as history does not say anything about pigeons.\n" +
                    "---\n" +
                    "E. Romeo and Juliet, the two characters from the play by William Shakespeare, are remembered all over the world as an emblem of romance. The young lovers lived in Verona, Italy. |Every Valentine’s Day, this city still receives about 1,000 letters addressed to Juliet. The fictional character is still alive for many people who believe in romantic and immortal love|.\n" +
                    "---\n" +
                    "F. Started by a group of feminists, Quirkyalone Day is celebrated on February 14 as an alternative to Valentine’s Day. The new holiday started in 2003 as a celebration of romance, freedom and individuality. |It’s a day to celebrate the things you enjoy doing alone. Ways to celebrate include: buying yourself a new dress, taking a long walk without your mobile phone, exploring a new part of town, trying a new recipe etc|.'";

            String text8 = "'About 250 miles off the coast of South America lie the Falkland Islands, a British overseas territory. About 3,000 people live on the islands. Like most isolated communities around the world, they are always pleased to welcome tourists. The people of the Falkland Islands mostly work in sheep farming and fishing.\n" +
                    "Everything outside Stanley, known locally as Camp, is home to numerous farms and settlements spread across the islands. In fact,over three quarters of the population live in Stanley. Although one of the smallest capitals in the world, Stanley provides a variety of supermarkets, excellent restaurants and hotels, a swimming pool, gym and golf course.\n" +
                    "Open whenever tour ships are in the port, the Falkland Islands Museum contains artifacts from everyday life, natural history samples and a fine collection relating to the islands’ shipwrecks. Outdoor exhibition sites include the Reclus Hut, originally made in Stanley, then shipped to Antarctica and set up there in 1956. Forty years later the famous house was brought back.\n" +
                    "There are only about 380 children of school age living on the islands. For them, there is a primary and a secondary school in Stanley and three small settlement schools on large farms. Other rural pupils are taught by ’travelling’ teachers. Schooling is free and compulsory for children between five and sixteen years of age. The government pays for older students to attend colleges, usually in the UK.\n" +
                    "The Falkland Islands government is taking advantage of cheap wind power. Since 1996, the government has been investing in the development of alternative sources of energy and can already enjoy the results. The Islands have experimented with other forms of energy, including hydro-electric and solar power. However, these forms cannot match the effectiveness of wind power yet.\n" +
                    "The Falklands War was fought in 1982 between Argentina and the United Kingdom. It started with the Argentine invasion and occupation of the Falkland Islands and South Georgia. The war lasted 74 days and ended with an Argentine defeat. However, Argentina still has not fully given up its claim to the territory of the islands.'";
            String task8 = "'Выберите вопрос\n" +
                    "1. What is the capital of the Falkland Islands?\n" +
                    "2. Where can you learn about the islands’ history?\n" +
                    "3. What is the emblem of the Falkland Islands?\n" +
                    "4. What do the local people do for a living? \n" +
                    "5. What energy resources are used on the islands?\n" +
                    "6. What is the educational policy of the Falkland Islands?\n" +
                    "7. What was the result of the conflict between Argentina and the UK?'";
            String answer8 = "'4 1 2 6 5 7'";
            String explanation8 = "'A. About 250 miles off the coast of South America lie the Falkland Islands, a British overseas territory. About 3,000 people live on the islands. Like most isolated communities around the world, they are always pleased to welcome tourists. |The people of the Falkland Islands mostly work in sheep farming and fishing|.\n" +
                    "---\n" +
                    "B. Everything outside Stanley, known locally as Camp, is home to numerous farms and settlements spread across the islands. In fact,over three quarters of the population live in Stanley. |Although one of the smallest capitals in the world, Stanley| provides a variety of supermarkets, excellent restaurants and hotels, a swimming pool, gym and golf course.\n" +
                    "---\n" +
                    "C. Open whenever tour ships are in the port, |the Falkland Islands Museum contains artifacts from everyday life, natural history samples and a fine collection relating to the islands’ shipwrecks. Outdoor exhibition sites include the Reclus Hut, originally made in Stanley, then shipped to Antarctica and set up there in 1956|. Forty years later the famous house was brought back.\n" +
                    "---\n" +
                    "D. There are only about 380 children of school age living on the islands. For them, there is a primary and a secondary school in Stanley and three small settlement schools on large farms. Other rural pupils are taught by ’travelling’ teachers. |Schooling is free and compulsory for children between five and sixteen years of age. The government pays for older students to attend colleges, usually in the UK|.\n" +
                    "---\n" +
                    "E. |The Falkland Islands government is taking advantage of cheap wind power|. Since 1996, the government has been investing in the development of alternative sources of energy and can already enjoy the results. The Islands have experimented with other forms of energy, including hydro-electric and solar power. However, these forms cannot match the effectiveness of wind power yet.\n" +
                    "---\n" +
                    "F. The Falklands War was fought in 1982 between Argentina and the United Kingdom. It started with the Argentine invasion and occupation of the Falkland Islands and South Georgia. |The war lasted 74 days and ended with an Argentine defeat. However, Argentina still has not fully given up its claim to the territory of the islands|.'";


            db.execSQL("UPDATE Reading_task1 SET text=" + text1 + ", task=" + task1 + ", answer=" +
                    answer1 + ", explanation=" + explanation1 + ", completion=0 WHERE _id=1");
            db.execSQL("UPDATE Reading_task1 SET text=" + text2 + ", task=" + task2 + ", answer=" +
                    answer2 + ", explanation=" + explanation2 + ", completion=0 WHERE _id=2");
            db.execSQL("UPDATE Reading_task1 SET text=" + text3 + ", task=" + task3 + ", answer=" +
                    answer3 + ", explanation=" + explanation3 + ", completion=0 WHERE _id=3");
            db.execSQL("UPDATE Reading_task1 SET text=" + text4 + ", task=" + task4 + ", answer=" +
                    answer4 + ", explanation=" + explanation4 + ", completion=0 WHERE _id=4");
            db.execSQL("UPDATE Reading_task1 SET text=" + text5 + ", task=" + task5 + ", answer=" +
                    answer5 + ", explanation=" + explanation5 + ", completion=0 WHERE _id=5");
            db.execSQL("UPDATE Reading_task1 SET text=" + text6 + ", task=" + task6 + ", answer=" +
                    answer6 + ", explanation=" + explanation6 + ", completion=0 WHERE _id=6");
            db.execSQL("UPDATE Reading_task1 SET text=" + text7 + ", task=" + task7 + ", answer=" +
                    answer7 + ", explanation=" + explanation7 + ", completion=0 WHERE _id=7");
            db.execSQL("UPDATE Reading_task1 SET text=" + text8 + ", task=" + task8 + ", answer=" +
                    answer8 + ", explanation=" + explanation8 + ", completion=0 WHERE _id=8");

            db.execSQL("DELETE FROM Reading_task1 WHERE _id=9");
            db.execSQL("DELETE FROM Reading_task1 WHERE _id=10");

            // очищаем Недавнюю активность, чтобы не оставить следов старых заданий
            db.execSQL("DELETE FROM Recent_activities");

            // инструкция к заданию изменилась
            editor.putBoolean("Dont_show_reading1", false);
            editor.apply();
        }
        //super.onUpgrade(db, oldVersion, newVersion);
    }
}
