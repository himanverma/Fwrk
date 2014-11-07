<div class="vendors view">
<h2><?php echo __('Vendor'); ?></h2>
	<dl>
		<dt><?php echo __('Id'); ?></dt>
		<dd>
			<?php echo h($vendor['Vendor']['id']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Name'); ?></dt>
		<dd>
			<?php echo h($vendor['Vendor']['name']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Photo'); ?></dt>
		<dd>
			<?php echo h($vendor['Vendor']['photo']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Company Logo'); ?></dt>
		<dd>
			<?php echo h($vendor['Vendor']['company_logo']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Company Name'); ?></dt>
		<dd>
			<?php echo h($vendor['Vendor']['company_name']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Address'); ?></dt>
		<dd>
			<?php echo h($vendor['Vendor']['address']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('City'); ?></dt>
		<dd>
			<?php echo h($vendor['Vendor']['city']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('State'); ?></dt>
		<dd>
			<?php echo h($vendor['Vendor']['state']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Country'); ?></dt>
		<dd>
			<?php echo h($vendor['Vendor']['country']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Email'); ?></dt>
		<dd>
			<?php echo h($vendor['Vendor']['email']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Password'); ?></dt>
		<dd>
			<?php echo h($vendor['Vendor']['password']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Mobile Number'); ?></dt>
		<dd>
			<?php echo h($vendor['Vendor']['mobile_number']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Phone Number'); ?></dt>
		<dd>
			<?php echo h($vendor['Vendor']['phone_number']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Lat'); ?></dt>
		<dd>
			<?php echo h($vendor['Vendor']['lat']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Long'); ?></dt>
		<dd>
			<?php echo h($vendor['Vendor']['long']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Status'); ?></dt>
		<dd>
			<?php echo h($vendor['Vendor']['status']); ?>
			&nbsp;
		</dd>
	</dl>
</div>
<div class="actions">
	<h3><?php echo __('Actions'); ?></h3>
	<ul>
		<li><?php echo $this->Html->link(__('Edit Vendor'), array('action' => 'edit', $vendor['Vendor']['id'])); ?> </li>
		<li><?php echo $this->Form->postLink(__('Delete Vendor'), array('action' => 'delete', $vendor['Vendor']['id']), array(), __('Are you sure you want to delete # %s?', $vendor['Vendor']['id'])); ?> </li>
		<li><?php echo $this->Html->link(__('List Vendors'), array('action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Vendor'), array('action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Combinations'), array('controller' => 'combinations', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Combination'), array('controller' => 'combinations', 'action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Vendor Days'), array('controller' => 'vendor_days', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Vendor Day'), array('controller' => 'vendor_days', 'action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Vendor Documents'), array('controller' => 'vendor_documents', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Vendor Document'), array('controller' => 'vendor_documents', 'action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Vendor Reviews'), array('controller' => 'vendor_reviews', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Vendor Review'), array('controller' => 'vendor_reviews', 'action' => 'add')); ?> </li>
	</ul>
</div>
<div class="related">
	<h3><?php echo __('Related Combinations'); ?></h3>
	<?php if (!empty($vendor['Combination'])): ?>
	<table cellpadding = "0" cellspacing = "0">
	<tr>
		<th><?php echo __('Id'); ?></th>
		<th><?php echo __('Vendor Id'); ?></th>
		<th><?php echo __('Display Name'); ?></th>
		<th><?php echo __('Day'); ?></th>
		<th><?php echo __('Date'); ?></th>
		<th><?php echo __('Price'); ?></th>
		<th><?php echo __('Status'); ?></th>
		<th class="actions"><?php echo __('Actions'); ?></th>
	</tr>
	<?php foreach ($vendor['Combination'] as $combination): ?>
		<tr>
			<td><?php echo $combination['id']; ?></td>
			<td><?php echo $combination['vendor_id']; ?></td>
			<td><?php echo $combination['display_name']; ?></td>
			<td><?php echo $combination['day']; ?></td>
			<td><?php echo $combination['date']; ?></td>
			<td><?php echo $combination['price']; ?></td>
			<td><?php echo $combination['status']; ?></td>
			<td class="actions">
				<?php echo $this->Html->link(__('View'), array('controller' => 'combinations', 'action' => 'view', $combination['id'])); ?>
				<?php echo $this->Html->link(__('Edit'), array('controller' => 'combinations', 'action' => 'edit', $combination['id'])); ?>
				<?php echo $this->Form->postLink(__('Delete'), array('controller' => 'combinations', 'action' => 'delete', $combination['id']), array(), __('Are you sure you want to delete # %s?', $combination['id'])); ?>
			</td>
		</tr>
	<?php endforeach; ?>
	</table>
<?php endif; ?>

	<div class="actions">
		<ul>
			<li><?php echo $this->Html->link(__('New Combination'), array('controller' => 'combinations', 'action' => 'add')); ?> </li>
		</ul>
	</div>
</div>
<div class="related">
	<h3><?php echo __('Related Vendor Days'); ?></h3>
	<?php if (!empty($vendor['VendorDay'])): ?>
	<table cellpadding = "0" cellspacing = "0">
	<tr>
		<th><?php echo __('Id'); ?></th>
		<th><?php echo __('Vendor Id'); ?></th>
		<th><?php echo __('Day Name'); ?></th>
		<th><?php echo __('Status'); ?></th>
		<th class="actions"><?php echo __('Actions'); ?></th>
	</tr>
	<?php foreach ($vendor['VendorDay'] as $vendorDay): ?>
		<tr>
			<td><?php echo $vendorDay['id']; ?></td>
			<td><?php echo $vendorDay['vendor_id']; ?></td>
			<td><?php echo $vendorDay['day_name']; ?></td>
			<td><?php echo $vendorDay['status']; ?></td>
			<td class="actions">
				<?php echo $this->Html->link(__('View'), array('controller' => 'vendor_days', 'action' => 'view', $vendorDay['id'])); ?>
				<?php echo $this->Html->link(__('Edit'), array('controller' => 'vendor_days', 'action' => 'edit', $vendorDay['id'])); ?>
				<?php echo $this->Form->postLink(__('Delete'), array('controller' => 'vendor_days', 'action' => 'delete', $vendorDay['id']), array(), __('Are you sure you want to delete # %s?', $vendorDay['id'])); ?>
			</td>
		</tr>
	<?php endforeach; ?>
	</table>
<?php endif; ?>

	<div class="actions">
		<ul>
			<li><?php echo $this->Html->link(__('New Vendor Day'), array('controller' => 'vendor_days', 'action' => 'add')); ?> </li>
		</ul>
	</div>
</div>
<div class="related">
	<h3><?php echo __('Related Vendor Documents'); ?></h3>
	<?php if (!empty($vendor['VendorDocument'])): ?>
	<table cellpadding = "0" cellspacing = "0">
	<tr>
		<th><?php echo __('Id'); ?></th>
		<th><?php echo __('Vendor Id'); ?></th>
		<th><?php echo __('Path'); ?></th>
		<th><?php echo __('Detail'); ?></th>
		<th class="actions"><?php echo __('Actions'); ?></th>
	</tr>
	<?php foreach ($vendor['VendorDocument'] as $vendorDocument): ?>
		<tr>
			<td><?php echo $vendorDocument['id']; ?></td>
			<td><?php echo $vendorDocument['vendor_id']; ?></td>
			<td><?php echo $vendorDocument['path']; ?></td>
			<td><?php echo $vendorDocument['detail']; ?></td>
			<td class="actions">
				<?php echo $this->Html->link(__('View'), array('controller' => 'vendor_documents', 'action' => 'view', $vendorDocument['id'])); ?>
				<?php echo $this->Html->link(__('Edit'), array('controller' => 'vendor_documents', 'action' => 'edit', $vendorDocument['id'])); ?>
				<?php echo $this->Form->postLink(__('Delete'), array('controller' => 'vendor_documents', 'action' => 'delete', $vendorDocument['id']), array(), __('Are you sure you want to delete # %s?', $vendorDocument['id'])); ?>
			</td>
		</tr>
	<?php endforeach; ?>
	</table>
<?php endif; ?>

	<div class="actions">
		<ul>
			<li><?php echo $this->Html->link(__('New Vendor Document'), array('controller' => 'vendor_documents', 'action' => 'add')); ?> </li>
		</ul>
	</div>
</div>
<div class="related">
	<h3><?php echo __('Related Vendor Reviews'); ?></h3>
	<?php if (!empty($vendor['VendorReview'])): ?>
	<table cellpadding = "0" cellspacing = "0">
	<tr>
		<th><?php echo __('Id'); ?></th>
		<th><?php echo __('Customer Id'); ?></th>
		<th><?php echo __('Vendor Id'); ?></th>
		<th><?php echo __('Review'); ?></th>
		<th><?php echo __('Ratings'); ?></th>
		<th class="actions"><?php echo __('Actions'); ?></th>
	</tr>
	<?php foreach ($vendor['VendorReview'] as $vendorReview): ?>
		<tr>
			<td><?php echo $vendorReview['id']; ?></td>
			<td><?php echo $vendorReview['customer_id']; ?></td>
			<td><?php echo $vendorReview['vendor_id']; ?></td>
			<td><?php echo $vendorReview['review']; ?></td>
			<td><?php echo $vendorReview['ratings']; ?></td>
			<td class="actions">
				<?php echo $this->Html->link(__('View'), array('controller' => 'vendor_reviews', 'action' => 'view', $vendorReview['id'])); ?>
				<?php echo $this->Html->link(__('Edit'), array('controller' => 'vendor_reviews', 'action' => 'edit', $vendorReview['id'])); ?>
				<?php echo $this->Form->postLink(__('Delete'), array('controller' => 'vendor_reviews', 'action' => 'delete', $vendorReview['id']), array(), __('Are you sure you want to delete # %s?', $vendorReview['id'])); ?>
			</td>
		</tr>
	<?php endforeach; ?>
	</table>
<?php endif; ?>

	<div class="actions">
		<ul>
			<li><?php echo $this->Html->link(__('New Vendor Review'), array('controller' => 'vendor_reviews', 'action' => 'add')); ?> </li>
		</ul>
	</div>
</div>
