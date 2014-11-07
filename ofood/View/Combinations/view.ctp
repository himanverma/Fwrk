<div class="combinations view">
<h2><?php echo __('Combination'); ?></h2>
	<dl>
		<dt><?php echo __('Id'); ?></dt>
		<dd>
			<?php echo h($combination['Combination']['id']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Vendor'); ?></dt>
		<dd>
			<?php echo $this->Html->link($combination['Vendor']['name'], array('controller' => 'vendors', 'action' => 'view', $combination['Vendor']['id'])); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Display Name'); ?></dt>
		<dd>
			<?php echo h($combination['Combination']['display_name']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Day'); ?></dt>
		<dd>
			<?php echo h($combination['Combination']['day']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Date'); ?></dt>
		<dd>
			<?php echo h($combination['Combination']['date']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Price'); ?></dt>
		<dd>
			<?php echo h($combination['Combination']['price']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Status'); ?></dt>
		<dd>
			<?php echo h($combination['Combination']['status']); ?>
			&nbsp;
		</dd>
	</dl>
</div>
<div class="actions">
	<h3><?php echo __('Actions'); ?></h3>
	<ul>
		<li><?php echo $this->Html->link(__('Edit Combination'), array('action' => 'edit', $combination['Combination']['id'])); ?> </li>
		<li><?php echo $this->Form->postLink(__('Delete Combination'), array('action' => 'delete', $combination['Combination']['id']), array(), __('Are you sure you want to delete # %s?', $combination['Combination']['id'])); ?> </li>
		<li><?php echo $this->Html->link(__('List Combinations'), array('action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Combination'), array('action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Vendors'), array('controller' => 'vendors', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Vendor'), array('controller' => 'vendors', 'action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Combination Items'), array('controller' => 'combination_items', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Combination Item'), array('controller' => 'combination_items', 'action' => 'add')); ?> </li>
	</ul>
</div>
<div class="related">
	<h3><?php echo __('Related Combination Items'); ?></h3>
	<?php if (!empty($combination['CombinationItem'])): ?>
	<table cellpadding = "0" cellspacing = "0">
	<tr>
		<th><?php echo __('Id'); ?></th>
		<th><?php echo __('Combination Id'); ?></th>
		<th><?php echo __('Recipe Id'); ?></th>
		<th><?php echo __('Name To Display'); ?></th>
		<th><?php echo __('Plate Size'); ?></th>
		<th class="actions"><?php echo __('Actions'); ?></th>
	</tr>
	<?php foreach ($combination['CombinationItem'] as $combinationItem): ?>
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
