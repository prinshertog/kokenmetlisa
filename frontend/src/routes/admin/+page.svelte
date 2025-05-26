<script lang="ts">
    let { data } = $props();
    const BACKEND_URL = "http://localhost:8080";

    let dishData = {
        dishName: "",
        dishDescription: "",
        dishCategory: "",
        dishSubCategory: "",
        dishImageURL: ""
    }

    let message = ""

    export async function addDish(event: SubmitEvent) {
        event.preventDefault();
        try {
            const response = await fetch(BACKEND_URL + "/dishes", {
                method: "POST",
                headers: { "Content-Type": "application/json", "Authorization": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYW4iLCJpYXQiOjE3NDgyNzIyNjIsImV4cCI6MTc0ODI3NTg2Mn0._s3-_TdVLMwclpL5WAzpyxYbnAfIE3-ab1kN6Cieve0"},
                body: JSON.stringify(dishData)
            });
            if (response.ok) {
               let result = await response.json()
               message = result;
            }
        } catch (error) {
            message = "Error " + error
        }
    }
</script>
<div class="flex justify-end">
    <div class="flex flex-wrap">
        <h1 class="p-2">Welcome {data.username}</h1>
        <p class="p-2 text-red-600"><a href="http://localhost:5173/logout">Logout</a></p>
    </div>
</div>
<div class="flex justify-center">
    <form class="flex flex-col border-1 p-5 m-10" method="POST" onsubmit={addDish}>
        <label for="dish-name">Add a dish:</label>
        <input bind:value={dishData.dishName} class="border-1 m-1" type="text"
            id="dish-name"
            name="Name"
            placeholder="Enter dish name"
            required
        />
        <textarea bind:value={dishData.dishDescription} class="border-1 m-1"
            name="Description"
            placeholder="Description"
            rows="5"
            cols="30"
            required
        ></textarea>
        <input bind:value={dishData.dishCategory} class="border-1 m-1" type="text"
            placeholder="Enter dish category"
            required>
        <input bind:value={dishData.dishSubCategory} class="border-1 m-1" type="text"
            placeholder="Enter dish subcategory"
            required>
        <input bind:value={dishData.dishImageURL} class="border-1 m-1" type="text"
            id="dish-image-url"
            name="Image Url"
            placeholder="Enter image URL"
            required
        />
        <input class="submit-button hover:cursor-pointer" type="submit"
            value="Add Dish"
        />
    </form>
    <p>{message}</p>
</div>

<div class="flex justify-center">
    {#each data.dishes as dish}
        <div class="border-1 rounded-xl p-5">
            <button class="text-white bg-red-600 float-right rounded-xl p-2 ml-1">Delete</button>
            <p class="text-xl pt-5">{dish.name}</p>
            <p>{dish.description}</p>
            <p class="text-xl pt-5">Category</p>
            <p>{dish.category.category} </p>
            <p class="text-xl pt-5">Image</p>
            <img class="h-100" src={dish.imageUrl} alt={dish.name} />
        </div>
    {/each}
</div>