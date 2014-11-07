<div class="recipes view">
<h2><?php echo __('Recipe'); ?></h2>
	<dl>
		<dt><?php echo __('Id'); ?></dt>
		<dd>
			<?php echo h($recipe['Recipe']['id']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Recipe Name'); ?></dt>
		<dd>
			<?php echo h($recipe['Recipe']['recipe_name']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Description'); ?></dt>
		<dd>
			<?php echo h($recipe['Recipe']['description']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Also Known As'); ?></dt>
		<dd>
			<?php echo h($recipe['Recipe']['also_known_as']); ?>
			&nbsp;
		</dd>
	</dl>
</div>
<div class="actions">
	<h3><?php echo __('Actions'); ?></h3>
	<ul>
		<li><?php echo $this->Html->link(__('Edit Recipe'), array('action' => 'edit', $recipe['Recipe']['id'])); ?> </li>
		<li><?php echo $this->Form->postLink(__('Delete Recipe'), array('action' => 'delete', $recipe['Recipe']['id']), array(), __('Are you sure you want to delete # %s?', $recipe['Recipe']['id'])); ?> </li>
		<li><?php echo $this->Html->link(__('List Recipes'), array('action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Recipe'), array('action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Combination Items'), array('controller' => 'combination_items', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Combination Item'), array('controller' => 'combination_items', 'action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Meal Menus'), array('controller' => 'meal_menus', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Meal Menu'), array('controller' => 'meal_menus', 'action' => 'add')); ?> </li>
	</ul>
</div>
<div class="related">
	<h3><?php echo __('Related Combination Items'); ?></h3>
	<?php if (!empty($recipe['CombinationItem'])): ?>
	<table cellpadding = "0" cellspacing = "0">
	<tr>
		<th><?php echo __('Id'); ?></th>
		<th><?php echo __('Combination Id'); ?></th>
		<th><?php echo __('Recipe Id'); ?></th>
		<th><?php echo __('Name To Display'); ?></th>
		<th><?php echo __('Plate Size'); ?></th>
		<th class="actions"><?php echo __('Actions'); ?></th>
	</tr>
	<?php foreach ($recipe['CombinationItem'] as $combinationItem): ?>
		<tr>
			<td><?php echo $combinationItem['id']; ?></td>
			<td><?php echo $combinationItem['combination_id']; ?></td>
			<td><?php echo $combinationItem['recipe_id']; ?></td>
			<td><?php echo $combinationItem['name_to_display']; ?></td>
			<td><?php echo $combinationItem['plate_size']; ?></td>
			<td class="actions">
				<?php echo $this->Html->link(__('View'), array('controller' => 'combination_items', 'action' => 'view', $combinationItem['id'])); ?>
				<?php echo $this->Html->link(__('Edit'), array('controller' => 'combination_items', 'action' => 'edit', $combinationItem['id'])); ?>
				<?php echo $this->Form->postLink(__('Delete'), array('controller' => 'combination_items', 'action' => 'delete', $combinationItem['id']), array(), __('Are you sure you want to delete # %s?', $combinationItem['id'])); ?>
			</td>
		</tr>
	<?php endforeach; ?>
	</table>
<?php endif; ?>

	<div class="actions">
		<ul>
			<li><?php echo $this->Html->link(__('New Combination Item'), array('controller' => 'combination_items', 'action' => 'add')); ?> </li>
		</ul>
	</div>
</div>
<div class="related">
	<h3><?php echo __('Related Meal Menus'); ?></h3>
	<?php if (!empty($recipe['MealMenu'])): ?>
	<table cellpadding = "0" cellspacing = "0">
	<tr>
		<th><?php echo __('Id'); ?></th>
		<th><?php echo __('Vendor Day Id'); ?></th>
		<th><?php echo __('Recipe Id'); ?></th>
		<th class="actions"><?php echo __('Actions'); ?></th>
	</tr>
	<?php foreach ($recipe['MealMenu'] as $mealMenu): ?>
		<tr>
			<td><?php echo $mealMenu['id']; ?></td>
			<td><?php echo $mealMenu['vendor_day_id']; ?></td>
			<td><?php echo $mealMenu['recipe_id']; ?></td>
			<td class="actions">
				<?php echo $this->Html->link(__('View'), array('controller' => 'meal_menus', 'action' => 'view', $mealMenu['id'])); ?>
				<?php echo $this->Html->link(__('Edit'), array('controller' => 'meal_menus', 'action' => 'edit', $mealMenu['id'])); ?>
				<?php echo $this->Form->postLink(__('Delete'), array('controller' => 'meal_menus', 'action' => 'delete', $mealMenu['id']), array(), __('Are you sure you want to delete # %s?', $mealMenu['id'])); ?>
			</td>
		</tr>
	<?php endforeach; ?>
	</table>
<?php endif; ?>

	<div class="actions">
		<ul>
			<li><?php echo $this->Html->link(__('New Meal Menu'), array('controller' => 'meal_menus', 'action' => 'add')); ?> </li>
		</ul>
	</div>
</div>
