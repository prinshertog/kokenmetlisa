<script>
    const { data, form } = $props();
    const { dish, error } = data;
</script>

<main class="flex justify-center">
    <!-- Forms Section -->
    <div>
        <!-- Add Dish Form -->
        <div class="bg-white shadow rounded-lg p-6">
            <h2 class="text-xl font-semibold mb-4">Update dish: {dish.name}</h2>
            <form method="POST" action="?/add" class="space-y-4" enctype="multipart/form-data">
                <div>
                    <input class="w-full px-3 py-2 border rounded-md" type="text"
                        name="name" placeholder="Dish name" required />
                </div>
                <div>
                    <textarea class="w-full px-3 py-2 border rounded-md"
                        name="description" placeholder="Description" rows="3" required></textarea>
                </div>
                <div>
                    <select class="w-full px-3 py-2 border rounded-md"
                        name="category" required>
                        <option value="">Select a category</option>
                        {#each data.categories as category}
                            <option value={category.category}>{category.category}</option>
                        {/each}
                    </select>
                </div>
                <div>
                    <input class="w-full px-3 py-2 border rounded-md" 
                        type="file" accept="image/*"
                        name="image" />
                </div>
                <button class="w-full bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700">
                    Add Dish
                </button>
            </form>
            {#if error}
                <p class="mt-2 text-red-500">{error}</p>
            {/if}
        </div>
    </div>
</main>