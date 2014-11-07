<div class="combinationItems view">
<h2><?php echo __('Combination Item'); ?></h2>
	<dl>
		<dt><?php echo __('Id'); ?></dt>
		<dd>
			<?php echo h($combinationItem['CombinationItem']['id']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Combination'); ?></dt>
		<dd>
			<?php echo $this->Html->link($combinationItem['Combination']['display_name'], array('controller' => 'combinations', 'action' => 'view', $combinationItem['Combination']['id'])); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Recipe'); ?></dt>
		<dd>
			<?php echo $this->Html->link($combinationItem['Recipe']['recipe_name'], array('controller' => 'recipes', 'action' => 'view', $combinationItem['Recipe']['id'])); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Name To Display'); ?></dt>
		<dd>
			<?php echo h($combinationItem['CombinationItem']['name_to_display']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Plate Size'); ?></dt>
		<dd>
			<?php echo h($combinationItem['CombinationItem']['plate_size']); ?>
			&nbsp;
		</dd>
	</dl>
</div>
<div class="actions">
	<h3><?php echo __('Actions'); ?></h3>
	<ul>
		<li><?php echo $this->Html->link(__('Edit Combination Item'), array('action' => 'edit', $combinationItem['CombinationItem']['id'])); ?> </li>
		<li><?php echo $this->Form->postLink(__('Delete Combination Item'), array('action' => 'delete', $combinationItem['CombinationItem']['id']), array(), __('Are you sure you want to delete # %s?', $combinationItem['CombinationItem']['id'])); ?> </li>
		<li><?php echo $this->Html->link(__('List Combination Items'), array('action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Combination Item'), array('action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Combinations'), array('controller' => 'combinations', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Combination'), array('controller' => 'combinations', 'action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Recipes'), array('controller' => 'recipes', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Recipe'), array('controller' => 'recipes', 'action' => 'add')); ?> </li>
	</ul>
</div>
