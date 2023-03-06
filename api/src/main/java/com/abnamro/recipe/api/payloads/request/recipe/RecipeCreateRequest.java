package com.abnamro.recipe.api.payloads.request.recipe;

import com.abnamro.recipe.api.constant.Validation;
import com.abnamro.recipe.api.validations.annotations.ValidEnumValue;
import com.abnamro.recipe.domain.entities.ERecipeType;
import com.abnamro.recipe.domain.entities.Ingredient;
import com.abnamro.recipe.domain.entities.Recipe;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class RecipeCreateRequest {

    @ApiModelProperty(notes = "Recipe Name", example = "Meat Stack")
    @Pattern(regexp = Validation.NAME_PATTERN, message = "Enter a valid Recipe Name")
    @NotBlank(message = "Recipe Name can't be blank")
    @Size(max = Validation.MAX_LENGTH_NAME, message = "Recipe Name Shouldn't be more than 100")
    private String name;

    @ApiModelProperty(notes = "Recipe Description", example = "Is a flat cut of beef with parallel faces")
    @Pattern(regexp = Validation.FREE_TEXT_PATTERN, message = "Enter a valid Recipe Description")
    @Size(max = Validation.MAX_LENGTH_FREE_TEXT, message = "Recipe Description Shouldn't be more than 255")
    private String description;

    @ApiModelProperty(notes = "Recipe Type", example = "VEGETARIAN")
    @ValidEnumValue(required = true, enumClass = ERecipeType.class)
    @NotBlank(message = "Recipe Type can't be blank")
    private String type;

    @ApiModelProperty(notes = "Recipe number of services", example = "3")
    @Positive(message = "Recipe number of servings should be positive")
    @NotNull(message = "Recipe number of servings can't be blank")
    private int numberOfServings;

    @ApiModelProperty(notes = "Recipe ingredients IDs", example = "[1,2]")
    @NotNull(message = "Recipe ingredients can't be empty")
    private Set<Long> ingredients;

    @ApiModelProperty(notes = "Recipe Created By", example = "example@example.com")
    @Pattern(regexp = Validation.EMAIL_PATTERN, message = "Enter a valid email")
    @NotBlank(message = "Recipe Created By Can't be blank")
    @Size(max = Validation.MAX_LENGTH_NAME, message = "Recipe Created By 'email' Shouldn't be more than 100")
    private String createdBy;

    public Recipe toEntity() {
        return Recipe.builder()
                .name(this.name)
                .description(this.description)
                .type(this.type != null ? ERecipeType.valueOf(this.type) : null)
                .numberOfServings(this.numberOfServings)
                .ingredients(this.ingredients.stream().map(
                        id -> Ingredient.builder().id(id).build()
                ).collect(Collectors.toSet()))
                .createdBy(this.createdBy)
                .build();
    }
}
