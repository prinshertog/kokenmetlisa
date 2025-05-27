<script lang="ts">
    import { marked } from 'marked';
    import type { PageData } from './$types';
    
    const { data } = $props();
    const { imageUrl, name, description, category, subcategory } = data;
    
    // Convert markdown to HTML
    let descriptionHtml = $derived(marked(description));
</script>

<div class="max-w-4xl mx-auto py-8 px-4">
    <div class="bg-white shadow rounded-lg overflow-hidden">
        <img class="w-full h-64 object-cover" src={imageUrl} alt={name} />
        <div class="p-6">
            <h1 class="text-3xl font-bold mb-4">{name}</h1>
            <div class="prose max-w-none">
                {descriptionHtml}
            </div>
            <div class="mt-4 text-sm text-gray-500">
                <p>Category: {category.category}</p>
                {#if subcategory}
                    <p>Subcategory: {subcategory}</p>
                {/if}
            </div>
        </div>
    </div>
</div>