<script lang="ts">
    import { marked } from 'marked';    
    const { data } = $props();
    const { imageName, name, description, categories } = data.dish;
    import { env } from '$env/dynamic/public';
    const BASE_URL_BACKEND = env.PUBLIC_BASE_URL_BACKEND;
    marked.setOptions({ breaks: true });
    
    // Convert markdown to HTML
    let formattedDescription = description.replace(/\r\n/g, " <br> ");
    let descriptionHtml = $derived(marked(formattedDescription));

</script>

<div class="max-w-4xl mx-auto py-8 px-4">
    <div class="bg-white shadow rounded-lg overflow-hidden">
        <img class="w-full h-64 object-cover" src='{`${BASE_URL_BACKEND}/file/full-${imageName}`}' alt={name} />
        <div class="p-6">
            <h1 class="text-3xl font-bold mb-4">{name}</h1>
            <div class="prose max-w-none">
                {@html descriptionHtml}
            </div>
            <div class="mt-4 text-sm text-gray-500">
                <p class="mt-4 text-sm text-gray-500">Categories:</p>
                <div class="flex overflow-auto">
                    {#each categories as category}
                    <p class="m-1">{category.category}</p>
                    {/each}
                </div>
            </div>
        </div>
    </div>
</div>