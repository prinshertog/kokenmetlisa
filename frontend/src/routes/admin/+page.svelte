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
        <input class="submit-button" type="submit"
            value="Add Dish"
        />
    </form>
</div>

<div class="dishes-list">
    {#each data.dishes as dish}
        <div class="dish">
            <button class="delete-button">Delete</button>
            <h2>{dish.name}</h2>
            <p>{dish.description}</p>
            <h3>Category</h3>
            <p>{dish.category.category} </p>
            <h3>Image</h3>
            <img src={dish.imageUrl} alt={dish.name} />
        </div>
    {/each}
</div>

<style>
form {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}

form input, textarea {
    width: 90%;
    max-width: 300px;
    min-width: 300px;
    border-radius: 10px;
    padding: 10px;
    border-style: solid;
    border-width: 1px;
    border-color: rgb(170, 170, 170);
    box-shadow: 5px 5px 5px rgb(200, 200, 200);
    margin: 5px;
}

form input:focus, textarea:focus {
    border-color: rgb(162, 0, 255);
    outline: none;
}

.submit-button:hover {
    cursor: pointer;
    background-color: rgb(255, 126, 103);
    color: white;
}

.delete-button {
    float: right;
    background-color: red;
    border-style: none;
    border-radius: 10px;
    padding: 5px;
    color: white;
}

.delete-button:hover {
    cursor: pointer;
    background-color: rgb(255, 126, 103);
}

.dishes-add {
    width: 100%;
    padding: 50px;
}

.dishes-list {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}

.dishes-list .dish {
    padding: 20px;
    margin: 10px;
    background-color: rgb(104, 127, 255);
    border-radius: 20px;
    box-shadow: 10px 10px 10px rgb(200, 200, 200);
    width: 70%;
    max-width: 500px;
    color: white;
}

.dishes-list .dish p {
    margin-bottom: 10px;
}

.dish img {
    margin-top: 10px;
    max-width: 100%;
    max-height: 200px;
}
</style>