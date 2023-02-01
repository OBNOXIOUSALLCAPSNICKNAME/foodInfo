package com.example.foodinfo.utils


fun queryExample() {
    /*
        SELECT * FROM recipe WHERE
        servings BETWEEN 4.0 AND 15.0
        AND ingredients <= 13.0
        AND time >= 255.0
        AND id IN (SELECT recipe_id FROM nutrient_of_recipe
            WHERE CASE
                WHEN info_id = '2' THEN total_value BETWEEN 6.0 AND 26.0
                WHEN info_id = '7' THEN total_value <= 26.0
                WHEN info_id = '12' THEN total_value >= 82.0
                ELSE NULL END
            GROUP BY recipe_id
            HAVING  count(recipe_id) = 3)
        AND id IN (SELECT recipe_id FROM (
            SELECT recipe_id, category_id FROM label_of_recipe
            INNER JOIN label_recipe_attr ON info_id = label_recipe_attr.id
                WHERE info_id IN (1, 4, 8, 9, 46, 49, 52, 53)
                GROUP BY recipe_id, category_id)
            GROUP BY recipe_id HAVING count(recipe_id) = 3)
        AND name LIKE '%beef%'
     */
}