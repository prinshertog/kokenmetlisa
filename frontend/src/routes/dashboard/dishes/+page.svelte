<script lang="ts">
  const { data, form } = $props();
  const { username, role, categories } = data;

  import type { Category } from '$lib/types/types.js';

  let mainCategories = $derived(categories.filter((cat: Category) => !cat.parentCategory));
  let getSubcategories = (parentCat: string) => {
    return categories.filter((cat: Category) =>
      cat.parentCategory && cat.parentCategory.name === parentCat
    );
  };

  let pageObject = $state(data.pageObject);
  let loading = $state(false);

  async function loadPage(p: number) {
    loading = true;
    const res = await fetch(`/api/dishes?page=${p}&size=20`);
    pageObject = await res.json();
    loading = false;
  }
</script>

<svelte:head>
    <title>Koken met Lisa | Dashboard</title>
</svelte:head>

<div class="min-h-screen bg-gray-50">
    <!-- Header -->
    <header class="bg-white shadow">
        <div class="max-w-7xl mx-auto py-4 px-4 sm:px-6 lg:px-8 flex justify-between items-center">
            <h1 class="text-2xl font-semibold text-gray-900">Welcome {username}</h1>
            <div class="flex items-center space-x-4">
                {#if role === 'ADMIN'}
                    <a href="/users" 
                        class="px-4 py-2 text-blue-600 hover:text-blue-800 font-medium">
                        Users
                    </a>
                {:else}
                    <a href="/users" 
                        class="px-4 py-2 text-blue-600 hover:text-blue-800 font-medium">
                        User Settings
                    </a>
                {/if}
                <a href="/dashboard/categories" 
                    class="px-4 py-2 text-blue-600 hover:text-blue-800 font-medium">
                    Categories
                </a>
                <a href="/dashboard/inspirationdishes" 
                    class="px-4 py-2 text-blue-600 hover:text-blue-800 font-medium">
                    Inspiration Dishes
                </a>
                <form action="?/logout" method="POST" class="inline">
                    <button class="px-4 py-2 text-red-600 hover:text-red-800 font-medium cursor-pointer">
                        Logout
                    </button>
                </form>
            </div>
        </div>
    </header>

    <main class="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
        <!-- Forms Section -->
        <div class="mb-8">
            <!-- Add Dish Form -->
            <div class="bg-white shadow rounded-lg p-6">
                {#if form?.dishError}
                    <p class="text-red-500 bg-gray-100 border-1 border-gray-200 rounded p-2 mb-5">{form.dishError}</p>
                {/if}
                <h2 class="text-xl font-semibold mb-4">Add a Dish</h2>
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
                        <div class="w-full px-3 py-2 border rounded-md">
                            {#each mainCategories as category}
                            <p class="bg-gray-50 rounded p-2 m-1">
                                <label for="{category.name}">{category.name}</label>
                                <input name="selected-categories" class="float-end" type="checkbox" id="{category.name}" value="{category.name}">
                            </p>
                                {#each getSubcategories(category.name) as subcategory}
                                <p class="bg-gray-100 rounded p-2 m-1 ml-4">
                                    <label for="{subcategory.name}">↳ {subcategory.name}</label>
                                    <input name="selected-categories" class="float-end" type="checkbox" id="{subcategory.name}" value="{subcategory.name}">
                                </p>
                                {/each}
                            {/each}
                        </div>
                    </div>
                    <div>
                        <input class="w-full px-3 py-2 border rounded-md" 
                            type="file" accept="image/*"
                            name="image" />
                    </div>
                    <button class="w-full bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700 cursor-pointer">
                        Add Dish
                    </button>
                </form>
            </div>
        </div>

        <!-- Dishes Grid -->
        {#if pageObject}
        <div class="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
            {#each pageObject.content as dish}
            <div class="bg-white shadow rounded-lg">
                <a href={`/dishes/${dish.id}`}>
                <img
                    class="w-full h-48 object-cover cursor-pointer"
                    src={`/file/${dish.imageName}`}
                    alt={dish.name}
                    loading="lazy"
                />
                </a>

                <div class="p-6">

                <div class="flex items-center gap-2">
                <form action="?/deleteDish" method="POST">
                    <input type="hidden" name="id" value={dish.id}>
                    <input type="hidden" name="imageName" value={dish.imageName}>

                    <button class="text-red-600 hover:text-red-700 cursor-pointer" type="submit">
                    Delete
                    </button>
                </form>

                <a href={`/update/${dish.id}`} class="text-blue-600 hover:text-blue-700">
                    Update
                </a>
                </div>

                </div>
            </div>
            {/each}
        </div>
        {/if}

        <div class="mt-8 flex justify-center gap-4">
        {#if pageObject?.first == false}
            <button
            onclick={() => loadPage(pageObject.number - 1)}
            class="rounded bg-gray-200 px-4 py-2 hover:bg-gray-300 cursor-pointer"
            >
            Vorige
            </button>
        {/if}

        <span class="px-4 py-2 font-semibold">
            Page {pageObject?.number}
        </span>

        {#if pageObject?.last == false}
            <button
            onclick={() => loadPage(pageObject.number + 1)}
            class="rounded bg-gray-200 px-4 py-2 hover:bg-gray-300 cursor-pointer"
            >
            Volgende
            </button>
        {/if}
        </div>
    </main>
</div>