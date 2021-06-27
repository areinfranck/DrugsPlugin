package me.theminddroid.drugs.models;

import org.bukkit.Material;

public abstract class DrugRecipe {
    public static class None extends DrugRecipe {}

    public static class VerticalShaped extends DrugRecipe {
        private final Material top;
        private final Material middle;
        private final Material bottom;

        public VerticalShaped(Material top, Material middle, Material bottom) {

            this.top = top;
            this.middle = middle;
            this.bottom = bottom;
        }

        public Material getTop() {
            return top;
        }

        public Material getMiddle() {
            return middle;
        }

        public Material getBottom() {
            return bottom;
        }
    }
}
