INSERT INTO tb_category (name, icon, sort) VALUES
('美食', '/icons/food.png', 1),
('休闲娱乐', '/icons/entertainment.png', 2),
('酒店住宿', '/icons/hotel.png', 3),
('丽人美发', '/icons/beauty.png', 4),
('生活服务', '/icons/life.png', 5);

INSERT INTO tb_shop (category_id, name, address, longitude, latitude, phone, cover_url, avg_price, rating) VALUES
(1, '海底捞火锅(中关村店)', '北京市海淀区中关村大街15号', 116.316833, 39.988495, '010-82618888', 'https://copilot-cn.bytedance.net/api/ide/v1/text_to_image?prompt=hotpot%20restaurant%20interior%20with%20boiling%20broth%20and%20fresh%20ingredients%20on%20table%2C%20warm%20lighting%2C%20modern%20dining&image_size=landscape_4_3', 128.00, 4.8),
(1, '外婆家(西单店)', '北京市西城区西单北大街176号', 116.373571, 39.912345, '010-66026666', 'https://copilot-cn.bytedance.net/api/ide/v1/text_to_image?prompt=chinese%20home%20style%20restaurant%20with%20traditional%20dishes%20on%20wooden%20table%2C%20cozy%20atmosphere&image_size=landscape_4_3', 68.00, 4.5),
(1, '全聚德烤鸭(前门店)', '北京市东城区前门大街30号', 116.397128, 39.899580, '010-67011379', 'https://copilot-cn.bytedance.net/api/ide/v1/text_to_image?prompt=peking%20roast%20duck%20sliced%20on%20white%20plate%20with%20pancakes%20and%20scallions%2C%20classic%20chinese%20cuisine&image_size=landscape_4_3', 158.00, 4.6),
(2, '万达影城(CBD店)', '北京市朝阳区建国路93号', 116.461447, 39.908714, '010-58208888', 'https://copilot-cn.bytedance.net/api/ide/v1/text_to_image?prompt=modern%20cinema%20lobby%20with%20popcorn%20and%20movie%20posters%2C%20neon%20lights&image_size=landscape_4_3', 55.00, 4.3),
(2, '大玩家超乐场(三里屯店)', '北京市朝阳区三里屯路19号', 116.454365, 39.933120, '010-64176688', 'https://copilot-cn.bytedance.net/api/ide/v1/text_to_image?prompt=indoor%20arcade%20game%20center%20with%20claw%20machines%20and%20racing%20games%2C%20colorful%20neon%20lights&image_size=landscape_4_3', 88.00, 4.2),
(3, '如家酒店(国贸店)', '北京市朝阳区建国门外大街1号', 116.461200, 39.908200, '010-65056666', 'https://copilot-cn.bytedance.net/api/ide/v1/text_to_image?prompt=clean%20budget%20hotel%20room%20with%20white%20bedding%20and%20city%20view%2C%20modern%20simple%20design&image_size=landscape_4_3', 299.00, 4.1),
(3, '汉庭酒店(王府井店)', '北京市东城区王府井大街45号', 116.410476, 39.915123, '010-65228888', 'https://copilot-cn.bytedance.net/api/ide/v1/text_to_image?prompt=comfortable%20hotel%20room%20with%20large%20window%20overlooking%20beijing%20cityscape%2C%20warm%20lighting&image_size=landscape_4_3', 358.00, 4.4),
(4, '文峰美发(朝阳大悦城店)', '北京市朝阳区朝阳北路101号', 116.473512, 39.922876, '010-85523366', 'https://copilot-cn.bytedance.net/api/ide/v1/text_to_image?prompt=modern%20hair%20salon%20interior%20with%20styling%20chairs%20and%20mirrors%2C%20elegant%20decor&image_size=landscape_4_3', 168.00, 4.0),
(5, '链家地产(望京店)', '北京市朝阳区望京西路50号', 116.471300, 39.991200, '010-64708888', 'https://copilot-cn.bytedance.net/api/ide/v1/text_to_image?prompt=real%20estate%20agency%20office%20with%20property%20listings%20on%20screen%2C%20professional%20setting&image_size=landscape_4_3', 0.00, 4.5),
(1, '西贝莜面村(望京店)', '北京市朝阳区望京街9号', 116.470100, 39.992300, '010-84726688', 'https://copilot-cn.bytedance.net/api/ide/v1/text_to_image?prompt=northwest%20chinese%20cuisine%20restaurant%20with%20youmian%20dishes%20and%20yogurt%20on%20table%2C%20rustic%20decor&image_size=landscape_4_3', 95.00, 4.7);

INSERT INTO tb_review (shop_id, user_id, content, rating, images) VALUES
(1, 1, '服务态度非常好，菜品新鲜，锅底味道正宗，下次还来！', 5, 'https://copilot-cn.bytedance.net/api/ide/v1/text_to_image?prompt=hotpot%20with%20fresh%20vegetables%20and%20meat%20slices%20in%20boiling%20broth%2C%20top%20view&image_size=square,https://copilot-cn.bytedance.net/api/ide/v1/text_to_image?prompt=hotpot%20dipping%20sauce%20bar%20with%20various%20condiments&image_size=square'),
(1, 2, '等位时间有点长，但味道确实不错，推荐番茄锅底。', 4, 'https://copilot-cn.bytedance.net/api/ide/v1/text_to_image?prompt=tomato%20hotpot%20broth%20boiling%20with%20ingredients%2C%20vibrant%20red%20color&image_size=square'),
(2, 3, '性价比很高，菜品分量足，茶香鸡是必点菜！', 5, NULL),
(3, 4, '烤鸭皮脆肉嫩，蘸酱很香，就是价格偏贵，偶尔吃一次还行。', 4, 'https://copilot-cn.bytedance.net/api/ide/v1/text_to_image?prompt=peking%20duck%20being%20sliced%20by%20chef%2C%20crispy%20golden%20skin&image_size=square'),
(4, 5, '影厅很大，座椅舒适，就是周末人太多需要提前买票。', 4, 'https://copilot-cn.bytedance.net/api/ide/v1/text_to_image?prompt=cinema%20interior%20with%20red%20seats%20and%20big%20screen&image_size=square'),
(5, 6, '游戏种类很多，适合朋友聚会，抓娃娃机有点坑。', 3, NULL),
(6, 7, '位置便利，房间干净整洁，早餐种类还可以。', 4, NULL),
(7, 8, '离王府井步行街很近，出行方便，房间隔音一般。', 4, 'https://copilot-cn.bytedance.net/api/ide/v1/text_to_image?prompt=hotel%20breakfast%20buffet%20with%20chinese%20and%20western%20options&image_size=square'),
(8, 9, '理发师手艺不错，环境也挺好，就是等位时间有点久。', 4, 'https://copilot-cn.bytedance.net/api/ide/v1/text_to_image?prompt=hair%20stylist%20cutting%20hair%20in%20modern%20salon&image_size=square'),
(9, 10, '经纪人很专业，带看了好几套房，服务态度好。', 5, NULL),
(10, 1, '莜面鱼鱼特别好吃，酸奶也很赞，适合带家人来。', 5, 'https://copilot-cn.bytedance.net/api/ide/v1/text_to_image?prompt=northwest%20chinese%20youmian%20noodles%20in%20bowl%2C%20traditional%20dish&image_size=square,https://copilot-cn.bytedance.net/api/ide/v1/text_to_image?prompt=chinese%20yogurt%20in%20clay%20pot%2C%20traditional%20dessert&image_size=square');
