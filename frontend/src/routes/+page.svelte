<script>
    let { data } = $props();
</script>

<div class="dishes-add">
    <form method="POST">
        <label for="dish-name">Add a dish:</label>
        <input type="text"
            id="dish-name"
            name="Name"
            placeholder="Enter dish name"
            required
        />
        <textarea
            name="Description"
            placeholder="Description"
            rows="5"
            cols="30"
            required
        ></textarea>
        <input type="text"
            id="dish-image-url"
            name="Image Url"
            placeholder="Enter image URL"
            required
        />
        <input type="submit"
            value="Add Dish"
        />
    </form>
</div>

<div class="dishes-list">
    {#each data.dishes as dish}
        <div class="dish">
            <h2>{dish.name}</h2>
            <p>{dish.description}</p>
            <h3>Category</h3>
            <p>{dish.category.category}</p>
            <h3>Sub Category</h3>
            <p>{dish.subCategory.subCategory}</p>
            <h3>Image</h3>
            <img src={dish['Image Url']} alt={dish.name} />
        </div>
    {/each}
</div>